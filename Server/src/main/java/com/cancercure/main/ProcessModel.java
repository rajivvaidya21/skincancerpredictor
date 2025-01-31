package com.cancercure.main;

import java.io.File;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

@Path("/processupload")
public class ProcessModel   {
	static String pythonFile = "/app.py";
	public static final String TMP_DIR = System.getProperty("java.io.tmpdir");
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response getData(@FormDataParam("imagefile") File image) {
		try {
			/*
			 * byte[] encoded =
			 * Base64.getEncoder().encode(FileUtils.readFileToByteArray(image)); String str
			 * = new String(encoded, StandardCharsets.UTF_8); ClassLoader classLoader =
			 * getClass().getClassLoader(); File file = new
			 * File(classLoader.getResource("app.py").getFile()); PythonInterpreter
			 * interpreter = null; Properties p = new Properties();
			 * p.setProperty("python.path",
			 * "C:\\Users\\RA378469\\Desktop\\jython-standalone-2.7.2");
			 * p.setProperty("python.home",
			 * "C:\\Users\\RA378469\\Desktop\\jython-standalone-2.7.2");
			 * p.setProperty("python.prefix",
			 * "C:\\Users\\RA378469\\Desktop\\jython-standalone-2.7.2");
			 * PythonInterpreter.initialize(System.getProperties(), p, new String[] {});
			 * interpreter = new PythonInterpreter();
			 * interpreter.execfile(file.getAbsolutePath()); //PyObject strH =
			 * interpreter.eval("repr(returnvalue('"+image.getAbsolutePath()+"'))");
			 * PyObject strH = interpreter.eval("repr(model_predict('"+str+"'))");
			 * System.out.println(strH.toString()); interpreter.close();
			 */
			Client client = ClientBuilder.newBuilder().register(MultiPartFeature.class).build();
			/*
			 * client.property(ClientProperties.CONNECT_TIMEOUT, 60000);
			 * client.property(ClientProperties.READ_TIMEOUT, 60000);
			 */
			WebTarget webTarget = 
					client.target("http://localhost:8089/processImage");
			MultiPart multiPart = new MultiPart();
	        multiPart.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);
	        FileDataBodyPart fileDataBodyPart = new FileDataBodyPart("file",image,MediaType.APPLICATION_OCTET_STREAM_TYPE);
	            multiPart.bodyPart(fileDataBodyPart);
			/*
			 * MultivaluedMap<String, File> formData = new MultivaluedHashMap<String,
			 * File>();
			 * 
			 * formData.add("file", image);
			 */
	            Response response = webTarget.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(multiPart, multiPart.getMediaType()));
	                System.out.println(response.getStatus() + " " + response.getStatusInfo() + " " + response);
	                String output = response.readEntity(String.class);
	               // Lesion les = new Lesion();
	                //les.setVascularlesions(vascularlesions);
	                System.out.println(output);
			    image.deleteOnExit();
				return Response.status(Response.Status.OK).entity(output).build();
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("Failed to process Image. "+ e1.toString());
			return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("Failed to process Image. "+ e1.toString()).build();
		}
		//return Response.status(Response.Status.NOT_ACCEPTABLE).build();
	}
	/*
	 * @Override public ResourceConfig configure() { return new
	 * ResourceConfig(TestPython.class) .register(MultiPartFeature.class); }
	 * 
	 * @Override public void configureClient(ClientConfig config) {
	 * config.register(MultiPartFeature.class); }
	 */
}