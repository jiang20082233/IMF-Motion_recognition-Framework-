package Motion.server;

import Motion.run.MotionCheck;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author CheolMin Kim
 * @author Hwasung Seo
 */
public class UltrasonicResource extends CoapResource {

	private static final Logger logger = LoggerFactory.getLogger(UltrasonicResource.class);
	
	
	private static UltrasonicResource instance;
	public static double ultraDistance;
	
	public UltrasonicResource() throws Exception {
		super("ultrasonic");
		instance = this;
		
		setObservable(true);
		getAttributes().setObservable();
		setObserveType(CoAP.Type.NON);
		
		Thread thread = new Thread(){
			@Override
			public void run() {
				while(true){
					try{
						changed();
						Thread.sleep(500);
					}catch(Exception e){
						LOGGER.info(e.toString());
					}
				}
			}
			
		}; 
		thread.start();
		
	}

	public static UltrasonicResource getInstance() {

		return instance;
	}

	@Override
	public void handleGET(CoapExchange exchange) {
		JSONObject responseJsonObject = new JSONObject();
		responseJsonObject.put("distance", String.valueOf(ultraDistance) );
		
		String responseJson = responseJsonObject.toString();
		exchange.respond(responseJson);
	}

	/**
	 * Methods to get data by Post method.
	 * This should be a String of Json type with two Keys and Values.
	 * The first key should be "sensor" and the Value should be "ultrasonic".
	 * The second key should be "distance" and Value should be the value of the distance measured by the Ultrasonic Sensor..
	 * @param exchange 
	 */
	@Override
	public void handlePOST(CoapExchange exchange) {
		//{"sensor":"ultrasonic","distance":"100"} 이런식으로
	//{"sensor":"status"} 이런식으로 요청

		try{
		String requestJson = exchange.getRequestText();
		JSONObject requestJsonObject = new JSONObject(requestJson);
		String sensor = requestJsonObject.getString("sensor");
		if (sensor.equals("ultrasonic")) {
			double distance= Double.parseDouble(requestJsonObject.getString("distance"));
			ultraDistance=distance;
			MotionCheck.ultrasonicAddData(distance);
	
		}else if (sensor.equals("status")) {

		}
		JSONObject responseJsonObject = new JSONObject();
		responseJsonObject.put("result", "success");
		responseJsonObject.put("distance", String.valueOf(ultraDistance));
		String responseJson = responseJsonObject.toString();
		exchange.respond(responseJson);
		}catch(Exception e){
		JSONObject responseJsonObject = new JSONObject();
		responseJsonObject.put("result", "fail");
		String responseJson = responseJsonObject.toString();
		exchange.respond(responseJson);
		}

	}

}
