

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Servlet implementation class GoogleApiPoc
 */
@WebServlet("/GoogleApiPoc")
public class GoogleApiPoc extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoogleApiPoc() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
				System.out.println(">>>>>>"+request.getParameter("trackingId"));
				JSONParser parse = new JSONParser();
				String googleAPIKey = request.getParameter("ApiKey");
				String latitude = request.getParameter("latitude");
				String longitude = request.getParameter("longitude");
				//String urlString = "https://maps.googleapis.com/maps/api/geocode/json?key="+googleAPIKey+"&latlng="+latitude+","+longitude+"&sensor=true&location_type=GEOMETRIC_CENTER\r\n";
				String urlString = "https://maps.googleapis.com/maps/api/geocode/json?key=AIzaSyDkAPNddvn7fFWcuwoHi02ItbTE4NglJi0&latlng=18.605289,73.76172300000002&sensor=true&location_type=GEOMETRIC_CENTER\r\n";
				URL obj = new URL(urlString);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod("GET");
				//con.setRequestProperty("User-Agent", USER_AGENT);
				int responseCode = con.getResponseCode();
				String address = "";
				
				System.out.println("GET Response Code :: " + responseCode);
				if (responseCode == HttpURLConnection.HTTP_OK) { // success
					BufferedReader in = new BufferedReader(new InputStreamReader(
							con.getInputStream()));
					String inputLine;
					StringBuffer response1 = new StringBuffer();

					while ((inputLine = in.readLine()) != null) {
						response1.append(inputLine);
					}
					in.close();
					String returnData = response1.toString(); 
					//String address = "";
					
					try {
						JSONObject jobj = (JSONObject)parse.parse(returnData);
						JSONArray jsonarr_1 = (JSONArray) jobj.get("results"); 
						for(int i=0;i<jsonarr_1.size();i++)
						{
						//Store the JSON objects in an array
						//Get the index of the JSON object and print the values as per the index
						JSONObject jsonobj_1 = (JSONObject)jsonarr_1.get(i);
						System.out.println("Elements under results array");
						System.out.println("\nPlace id: " +jsonobj_1.get("place_id"));
						System.out.println("Types:  "+jsonobj_1.get("types"));
						}
						
						int count =0; 
						for(int i=0;i<jsonarr_1.size();i++)
						{
							count++;
							  if(count>1)
								  break;
						  //Store the JSON objects in an array
						  //Get the index of the JSON object and print the values as per the index
						  JSONObject jsonobj_1 = (JSONObject)jsonarr_1.get(i);
						  //Store the JSON object in JSON array as objects (For level 2 array element i.e Address Components)
						  JSONArray jsonarr_2 = (JSONArray) jsonobj_1.get("address_components");
						  System.out.println("Elements under results array");
						  System.out.println("\nPlace id: " +jsonobj_1.get("place_id"));
						  System.out.println("Types: " +jsonobj_1.get("types"));
						  //Get data for the Address Components array
						  System.out.println("Elements under address_components array");
						  System.out.println("The long names, short names and types are:");
						  
						  for(int j=0;j<jsonarr_2.size();j++)
						  {
							  
						     //Same just store the JSON objects in an array
						     //Get the index of the JSON objects and print the values as per the index
						     JSONObject jsonobj_2 = (JSONObject) jsonarr_2.get(j);
						     //Store the data as String objects
						     String str_data1 = (String) jsonobj_2.get("long_name");
						     System.out.println(str_data1);
						     address += ","+str_data1;
						     String str_data2 = (String) jsonobj_2.get("short_name");
						     System.out.println(str_data2);
						     System.out.println(jsonobj_2.get("types"));
						     System.out.println("\n");
						  }
						}
						
					}catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}
				
				response.getWriter().append("Served at: ").append(address);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
