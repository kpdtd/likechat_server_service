package com.fun.likechat.interceptor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fun.likechat.util.AESCrypto;
import com.fun.likechat.util.LogFactory;
import com.fun.likechat.util.Tools;

/**
 * 加解密的Json解析器
 * @author 
 */
public class AESJackson2HttpMessageConverter extends AbstractHttpMessageConverter<Object> implements GenericHttpMessageConverter<Object> {
	
	private static final Logger logger = LogFactory.getInstance().getLogger();

	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	private static final boolean jackson23Available = ClassUtils.hasMethod(ObjectMapper.class,"canDeserialize", JavaType.class, AtomicReference.class);

	private final List<Charset> availableCharsets;

	private boolean writeAcceptCharset = true;

	protected ObjectMapper objectMapper;

	private Boolean prettyPrint;
	
	public AESJackson2HttpMessageConverter() {
		this(Jackson2ObjectMapperBuilder.json().build());
	}

	protected AESJackson2HttpMessageConverter(ObjectMapper objectMapper) {
	    super(MediaType.ALL);
		this.objectMapper = objectMapper;
		this.availableCharsets = new ArrayList<Charset>(Charset.availableCharsets().values());
	}

	protected AESJackson2HttpMessageConverter(ObjectMapper objectMapper, MediaType supportedMediaType) {
		super(supportedMediaType);
		this.objectMapper = objectMapper;
		this.availableCharsets = new ArrayList<Charset>(Charset.availableCharsets().values());
	}

	protected AESJackson2HttpMessageConverter(ObjectMapper objectMapper, MediaType... supportedMediaTypes) {
		super(supportedMediaTypes);
		this.objectMapper = objectMapper;
		this.availableCharsets = new ArrayList<Charset>(Charset.availableCharsets().values());
	}

	public void setObjectMapper(ObjectMapper objectMapper) {
		Assert.notNull(objectMapper, "ObjectMapper must not be null");
		this.objectMapper = objectMapper;
		configurePrettyPrint();
	}

	public ObjectMapper getObjectMapper() {
		return this.objectMapper;
	}

	public void setPrettyPrint(boolean prettyPrint) {
		this.prettyPrint = prettyPrint;
		configurePrettyPrint();
	}

	private void configurePrettyPrint() {
		if (this.prettyPrint != null) {
			this.objectMapper.configure(SerializationFeature.INDENT_OUTPUT, this.prettyPrint);
		}
	}
	
	public void setWriteAcceptCharset(boolean writeAcceptCharset) {
		this.writeAcceptCharset = writeAcceptCharset;
	}

	@Override
	public boolean canRead(Class<?> clazz, MediaType mediaType) {
		return canRead(clazz, null, mediaType);
	}

	@Override
	public boolean canRead(Type type, Class<?> contextClass, MediaType mediaType) {
		JavaType javaType = getJavaType(type, contextClass);
		if (!jackson23Available || !logger.isWarnEnabled()) {
			return (canRead(mediaType) && this.objectMapper.canDeserialize(javaType));
		}
		return false;
	}

	@Override
	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
		if(mediaType == null) 
		    mediaType=MediaType.ALL;
		if (!jackson23Available || !logger.isWarnEnabled()) {
			return (canWrite(mediaType) && this.objectMapper.canSerialize(clazz));
		}
		return false;
	}

	@Override
	protected boolean supports(Class<?> clazz) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
		JavaType javaType = getJavaType(clazz, null);
		return readJavaType(javaType, inputMessage);
	}

	@Override
	public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
		JavaType javaType = getJavaType(type, contextClass);
		return readJavaType(javaType, inputMessage);
	}

	private Object readJavaType(JavaType javaType, HttpInputMessage inputMessage) {
		try {
			Charset charset = getContentTypeCharset(inputMessage.getHeaders().getContentType());
			String str = StreamUtils.copyToString(inputMessage.getBody(), charset);
			logger.info("URLDecoder.decode之前的数据："+str);
			//str=URLDecoder.decode(str,charset.toString());
			logger.info("获取数据解密前："+str);
			try {
				//str = AESCrypto.decrypt(str);
				logger.info("接收到 APPJSON串："+str);
				str = Tools.cleanXSS(str);
			} catch (Exception e) {
				logger.info("解密获取的数据发生异常!!!");
				str = null;
			}
			
			if(str != null) {
				//logger.info("获取数据解密后："+str);
				
				//过滤一些特殊字符
				try {
					str = str.replace("\\0", "");
				} catch (Exception e) {
					logger.info("过滤特殊字符发生异常!!!");
				}
				
				if(javaType.getRawClass() == String.class) {
					return str;
				} else {
					return this.objectMapper.readValue(str, javaType);
				}
			}
			
			return "{}";
		}
		catch (IOException ex) {
			throw new HttpMessageNotReadableException("Could not read JSON: " + ex.getMessage(), ex);
		}
	}
	
	/**
	 * 返回加密后的字符串长度
	 * @param str
	 * @param contentType
	 * @return
	 */
	private Long getEncoderContentLength(String str , MediaType contentType) {
		Charset charset = getContentTypeCharset(contentType);
		try {
			return (long) str.getBytes(charset.name()).length;
		}
		catch (UnsupportedEncodingException ex) {
			throw new IllegalStateException(ex);
		}
	}

	@Override
	protected void writeInternal(Object object, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
		try {
			Class<?> clazz = object.getClass();
			String str = null;
			if(clazz == String.class || clazz == StringBuilder.class || clazz == StringBuffer.class) {
				str = String.valueOf(object);
			} else {
				str = this.objectMapper.writeValueAsString(object);
			}
			
			logger.info("返回数据加密前："+str);
			try {
				//str = AESCrypto.encrypt(str);//加密后的字符串
			} catch (Exception e) {
				logger.info("返回数据加密的时候发生异常!!!");
				
				str = null;
			}
			if(str != null) {
				//logger.info("返回数据加密后："+str);
				
				HttpHeaders headers = outputMessage.getHeaders();
				if (headers.getContentLength() == -1) {
					Long contentLength = getEncoderContentLength(str, headers.getContentType());
					if (contentLength != null) {
						headers.setContentLength(contentLength);
					}
				}
				
				if (this.writeAcceptCharset) {
					headers.setAcceptCharset(getAcceptedCharsets());
				}
				Charset charset = getContentTypeCharset(outputMessage.getHeaders().getContentType());
				StreamUtils.copy(str, charset, outputMessage.getBody());
				outputMessage.getBody().flush();
			}
		}
		catch (JsonProcessingException ex) {
			throw new HttpMessageNotWritableException("Could not write content: " + ex.getMessage(), ex);
		}
	}
	
	protected List<Charset> getAcceptedCharsets() {
		return this.availableCharsets;
	}
	
	protected JavaType getJavaType(Type type, Class<?> contextClass) {
		return this.objectMapper.getTypeFactory().constructType(type, contextClass);
	}

	@Override
	protected MediaType getDefaultContentType(Object object) throws IOException {
		if (object instanceof MappingJacksonValue) {
			object = ((MappingJacksonValue) object).getValue();
		}
		return super.getDefaultContentType(object);
	}

	@Override
	protected Long getContentLength(Object object, MediaType contentType) throws IOException {
		if (object instanceof MappingJacksonValue) {
			object = ((MappingJacksonValue) object).getValue();
		}
		return super.getContentLength(object, contentType);
	}
	
	private Charset getContentTypeCharset(MediaType contentType) {
		if (contentType != null && contentType.getCharSet() != null) {
			return contentType.getCharSet();
		}
		else {
			return DEFAULT_CHARSET;
		}
	}

}
