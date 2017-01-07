package introsde.rest.processCentric;

import introsde.rest.models.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


import javax.ejb.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.glassfish.jersey.client.ClientConfig;
import org.json.*;
import org.apache.http.client.ClientProtocolException;



@Stateless // will work only inside a Java EE application
@LocalBean // will work only inside a Java EE application
@Path("/processCentric")
public class ProcessCentric {

	
    @PUT
    @Path("/updateHP")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateHP(LifeStatus ls) throws IOException {

        //Update measures of life status
        //String ENDPOINT = "http://10.218.204.124:5900/introsde/storage/updateHP/1";
        String ENDPOINT = "https://sdestoragehisyam.herokuapp.com/introsde/storage/updateHP/1";
        ClientConfig clientConfig = new ClientConfig();
        Client client = ClientBuilder.newClient(clientConfig);
        
        WebTarget service = client.target(ENDPOINT);

        Response res = null;
        String putResp = null;
        
        String updateHP ="{" + "\"measureName\":\""+ls.getMeasureName()+"\","
                        + "\"value\":\""+ls.getValue()+"\"}";
        
        res = service.request(MediaType.APPLICATION_JSON_TYPE).put(Entity.json(updateHP));
        putResp = res.readEntity(String.class);
        
        if(res.getStatus() != 200 ){
            return Response.status(400).build();
        }
        
        // Comparing the updated life status measures with the goals
       // String ENDPOINT2 = "http://10.218.204.124:5500/introsde/businessLogic/compare/"+ls.getMeasureName();
        String ENDPOINT2 = "https://sdebusinesslogichisyam.herokuapp.com/introsde/businessLogic/compare/"+ls.getMeasureName();
        DefaultHttpClient client1 = new DefaultHttpClient();
        HttpGet request = new HttpGet(ENDPOINT2);
        HttpResponse response = client1.execute(request);
         
        
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
         
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
             
        }
         
        JSONObject o = new JSONObject(result.toString());
        
        
        if(response.getStatusLine().getStatusCode() != 200){
            return Response.status(204).build();
            		
        }
        return Response.ok().build();
    }
        @PUT
        @Path("/updateGoal")
        @Produces(MediaType.APPLICATION_JSON)
        @Consumes(MediaType.APPLICATION_JSON)
        public Response updateGoal(Goal goal) throws IOException {
            
            //Update goal
            //String ENDPOINT = "http://10.218.204.124:5900/introsde/storage/updateGoal";
        	String ENDPOINT = "https://sdestoragehisyam.herokuapp.com/introsde/storage/updateGoal";
            ClientConfig clientConfig = new ClientConfig();
            Client client = ClientBuilder.newClient(clientConfig);
            
            WebTarget service = client.target(ENDPOINT);

            Response res = null;
            String putResp = null;
            
            String updateGoal ="{"
                        + "\"type\": \""+goal.getType()+"\","
                        + "\"value\": \""+goal.getValue()+"\","
                        + "\"idGoal\" : \""+goal.getIdGoal()+"\"}";
            
            res = service.request(MediaType.APPLICATION_JSON_TYPE).put(Entity.json(updateGoal));
            putResp = res.readEntity(String.class);
            
            if(res.getStatus() != 200 ){
                return Response.status(400).build();
            }
            
            return Response.ok(goal).build();

            
        }
      //Getting a motivation quote from quotedesign
        @GET
        @Path("/getQuote")
        public Response getQuote() throws ClientProtocolException, IOException {
       	
        	//String ENDPOINT = "http://10.218.204.124:5900/introsde/storage/getQuoteMotivation";
        	String ENDPOINT = "https://sdestoragehisyam.herokuapp.com/introsde/storage/getQuoteMotivation";

        	DefaultHttpClient client = new DefaultHttpClient();
        	HttpGet request = new HttpGet(ENDPOINT);
        	HttpResponse response = client.execute(request);
       	
        	BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        	StringBuffer result = new StringBuffer();
        	String line = "";
        	while ((line = rd.readLine()) != null) {
        	    result.append(line);
        	}
        	
        	JSONObject o = new JSONObject(result.toString());
        	
        	if(response.getStatusLine().getStatusCode() == 200){
        		return Response.ok(o.toString()).build();
            }
       
        	return Response.status(204).build();
       	
        }
        
        //Getting a motivation quote from forismatic
        @GET
        @Path("/getQuote2")
        public Response getQuote2() throws ClientProtocolException, IOException {
       	
        	//String ENDPOINT = "http://10.218.204.124:5900/introsde/storage/getQuoteMotivation2";
        	String ENDPOINT = "https://sdestoragehisyam.herokuapp.com/introsde/storage/getQuoteMotivation2";
        	DefaultHttpClient client = new DefaultHttpClient();
        	HttpGet request = new HttpGet(ENDPOINT);
        	HttpResponse response = client.execute(request);
       	
        	BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        	StringBuffer result = new StringBuffer();
        	String line = "";
        	while ((line = rd.readLine()) != null) {
        	    result.append(line);
        	}
        	
        	JSONObject o = new JSONObject(result.toString());
        	
        	if(response.getStatusLine().getStatusCode() == 200){
        		return Response.ok(o.toString()).build();
            }
       
        	return Response.status(204).build();
       	
        }
        
        
    }

      