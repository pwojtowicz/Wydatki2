package pl.wppiotrek85.wydatkibase.providers;

import java.io.IOException;
import java.io.StringWriter;

import javax.net.ssl.HttpsURLConnection;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.StdSerializerProvider;
import org.codehaus.jackson.type.TypeReference;

import pl.wppiotrek85.wydatkibase.entities.ItemsContainer;
import pl.wppiotrek85.wydatkibase.entities.ModelBase;
import pl.wppiotrek85.wydatkibase.entities.Transaction;
import pl.wppiotrek85.wydatkibase.interfaces.IHttpRequestToAsyncTaskCommunication;

public class Provider<T> {

	private String errorMessage = null;
	private final Class<T> classObject;

	public Provider(Class<T> classObject) {
		super();
		this.classObject = classObject;
	}

	public T getObjects(String url,
			IHttpRequestToAsyncTaskCommunication listener)
			throws CommunicationException {
		HTTPRequestBundle bundle = HTTPRequestProvider.sendRequest(
				HTTPRequestType.GET, url, listener);
		try {

			if (bundle.getStatusCode() == HttpsURLConnection.HTTP_OK
					|| bundle.getStatusCode() == 2000) {
				ObjectMapper mapper = new ObjectMapper();
				T items;
				items = mapper.readValue(bundle.getResponse(), classObject);
				return items;
			} else if (bundle.getStatusCode() == HttpsURLConnection.HTTP_NOT_MODIFIED) {
				throw new CommunicationException("The same file on device",
						ExceptionErrorCodes.TheSameFile);
			} else if (bundle.getStatusCode() == HttpsURLConnection.HTTP_UNAUTHORIZED) {
				throw new CommunicationException("",
						ExceptionErrorCodes.WrongCredentials);
			} else if (bundle.getStatusCode() == HttpsURLConnection.HTTP_NOT_FOUND) {
				throw new CommunicationException("",
						ExceptionErrorCodes.File_Not_Found);
			} else if (bundle.getStatusCode() == HttpsURLConnection.HTTP_INTERNAL_ERROR) {
				throw new CommunicationException("",
						ExceptionErrorCodes.WebServiceError);
			} else {

				throw new CommunicationException("",
						ExceptionErrorCodes.CommunicationProblems);
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
			errorMessage = e.getMessage();
		} catch (JsonMappingException e) {
			e.printStackTrace();
			errorMessage = e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			errorMessage = e.getMessage();
		}
		return null;
	}

	public int deleteObject(String url,
			IHttpRequestToAsyncTaskCommunication listener)
			throws CommunicationException {

		HTTPRequestBundle bundle = HTTPRequestProvider.sendRequest(
				HTTPRequestType.DELETE, url, listener);
		if (bundle.getStatusCode() == HttpsURLConnection.HTTP_OK) {
			return bundle.getStatusCode();
		} else if (bundle.getStatusCode() == HttpsURLConnection.HTTP_UNAUTHORIZED) {
			throw new CommunicationException("",
					ExceptionErrorCodes.WrongCredentials);
		} else if (bundle.getStatusCode() == HttpsURLConnection.HTTP_INTERNAL_ERROR) {
			throw new CommunicationException("",
					ExceptionErrorCodes.WebServiceError);
		} else {
			throw new CommunicationException(bundle.getResponse(),
					ExceptionErrorCodes.CommunicationProblems);
		}

	}

	public boolean sendObjects(String url, ItemsContainer<ModelBase> object,
			Boolean isReturnObjectId,
			IHttpRequestToAsyncTaskCommunication listener)
			throws CommunicationException {
		boolean result = false;
		ObjectMapper mapperRequest = new ObjectMapper();
		StringWriter sw = new StringWriter();
		try {
			StdSerializerProvider sp = new StdSerializerProvider();
			sp.setNullValueSerializer(new NullSerializer());
			mapperRequest.setSerializerProvider(sp);
			mapperRequest.writeValue(sw, object);

			HTTPRequestBundle bundle = HTTPRequestProvider.sendRequest(
					HTTPRequestType.POST, url, listener, sw.toString());
			if (bundle.getStatusCode() == HttpsURLConnection.HTTP_OK) {

				String response = bundle.getResponse();
				if (response.length() > 0) {
					if (response.equals("true"))
						result = true;
				}
			}
		} catch (Exception e) {

		}

		return result;

	}

	public T sendObject(String url, ModelBase object, Boolean isReturnObjectId,
			IHttpRequestToAsyncTaskCommunication listener)
			throws CommunicationException {
		ObjectMapper mapperRequest = new ObjectMapper();
		StringWriter sw = new StringWriter();

		try {
			StdSerializerProvider sp = new StdSerializerProvider();
			sp.setNullValueSerializer(new NullSerializer());
			mapperRequest.setSerializerProvider(sp);
			mapperRequest.writeValue(sw, object);

			HTTPRequestBundle bundle = HTTPRequestProvider.sendRequest(
					HTTPRequestType.POST, url, listener, sw.toString());

			if (bundle.getStatusCode() == HttpsURLConnection.HTTP_OK) {
				if (isReturnObjectId)
					object.setId(Integer.parseInt(bundle.getResponse()));
				else {
					if (bundle.getResponse().length() > 0) {
						// Dodane bo potrzebne by�o przy wykonywaniu akcji do
						// wykonania "pustej" akcji gdy nie ma nawet grupy
						// przys�anej
						ObjectMapper mapper = new ObjectMapper();
						return mapper.readValue(bundle.getResponse(),
								classObject);
					}
				}
				return (T) object;
			} else if (bundle.getStatusCode() == HttpsURLConnection.HTTP_UNAUTHORIZED) {
				throw new CommunicationException("",
						ExceptionErrorCodes.WrongCredentials);
			} else if (bundle.getStatusCode() == 403) {
				throw new CommunicationException("",
						ExceptionErrorCodes.SerwerInputTimeout);
			} else if (bundle.getStatusCode() == HttpsURLConnection.HTTP_BAD_REQUEST) {

				throw new CommunicationException("",
						ExceptionErrorCodes.WebServiceError);
			} else if (bundle.getStatusCode() == HttpsURLConnection.HTTP_INTERNAL_ERROR) {

				throw new CommunicationException("",
						ExceptionErrorCodes.WebServiceError);
			} else if (bundle.getStatusCode() == 503) {
				throw new CommunicationException("",
						ExceptionErrorCodes.CommunicationProblems);
			} else if (bundle.getStatusCode() == 2000) {
				throw new CommunicationException("",
						ExceptionErrorCodes.SendInformationOffline);
			} else {
				throw new CommunicationException(bundle.getResponse(),
						ExceptionErrorCodes.CommunicationProblems);
			}

		} catch (JsonParseException e) {
			e.printStackTrace();
			errorMessage = e.getMessage();
		} catch (JsonMappingException e) {
			e.printStackTrace();
			errorMessage = e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			errorMessage = e.getMessage();
		} finally {
			if (errorMessage != null)
				throw new CommunicationException(errorMessage,
						ExceptionErrorCodes.CommunicationProblems);
		}
		return null;
	}

	public ItemsContainer<Transaction> getTransactions(int from, int to,
			String url, IHttpRequestToAsyncTaskCommunication listener)
			throws CommunicationException {

		HTTPRequestBundle bundle = HTTPRequestProvider.sendRequest(
				HTTPRequestType.GET, url, listener);

		try {
			if (bundle.getStatusCode() == 200) {
				ObjectMapper mapper = new ObjectMapper();
				ItemsContainer<Transaction> itemsContainer = new ItemsContainer<Transaction>();
				itemsContainer = mapper.readValue(bundle.getResponse(),
						new TypeReference<ItemsContainer<Transaction>>() {
						});
				return itemsContainer;
			} else {

				throw new CommunicationException(bundle.getResponse(),
						ExceptionErrorCodes.CommunicationProblems);
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
			errorMessage = e.getMessage();
		} catch (JsonMappingException e) {
			e.printStackTrace();
			errorMessage = e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			errorMessage = e.getMessage();
		} finally {
			if (errorMessage != null)
				throw new CommunicationException(errorMessage,
						ExceptionErrorCodes.CommunicationProblems);
		}
		return null;
	}
}

class NullSerializer extends JsonSerializer<Object> {
	@Override
	public void serialize(Object value, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {

		jgen.writeNull();
	}
}
