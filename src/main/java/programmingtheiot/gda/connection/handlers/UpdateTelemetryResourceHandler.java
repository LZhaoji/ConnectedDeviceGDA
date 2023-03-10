package programmingtheiot.gda.connection.handlers;

import java.util.logging.Logger;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.CodeClass;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;

import programmingtheiot.common.ConfigConst;
import programmingtheiot.common.ConfigUtil;
import programmingtheiot.common.IDataMessageListener;
import programmingtheiot.common.ResourceNameEnum;
import programmingtheiot.data.DataUtil;
import programmingtheiot.data.SensorData;
import programmingtheiot.data.SystemPerformanceData;

public class UpdateTelemetryResourceHandler extends CoapResource{
    private static final Logger _Logger =
            Logger.getLogger(UpdateTelemetryResourceHandler.class.getName());
    
    private IDataMessageListener dataMsgListener = null;
    /**
     * Constructor.
     * @param resource Basically, the path (or topic)
     */
    public UpdateTelemetryResourceHandler(ResourceNameEnum resource)
    {
        this(resource.getResourceName());
    }
    /**
     * Constructor
     * @param resourceName The name of the resource.
     */
    public UpdateTelemetryResourceHandler(String resourceName) 
    {
        super(resourceName);
    }
    
    public void setDataMessageListener(IDataMessageListener listener)
    {
        if (listener != null) {
            this.dataMsgListener = listener;
        }
    }
    
    //处理来自sensor的get请求
    @Override
    public void handleGET(CoapExchange exchange) {
        ResponseCode code = ResponseCode.CONTENT;
        exchange.accept();
        
        SensorData sd = new SensorData();
        String sdStr = DataUtil.getInstance().sensorDataToJson(sd);
        
        _Logger.info("Got a request from the client.");
        
        exchange.respond(code, sdStr);
    }
    @Override
    public void handlePOST(CoapExchange exchange) {
        // TODO Auto-generated method stub
        ResponseCode code = ResponseCode.NOT_ACCEPTABLE;
        
        exchange.accept();
        
        if (this.dataMsgListener != null) {
            try {
                String jsonData = new String(exchange.getRequestPayload());
                SensorData sensorData = DataUtil.getInstance().jsonToSensorData(jsonData);
                this.dataMsgListener.handleSensorMessage(ResourceNameEnum.CDA_SENSOR_MSG_RESOURCE, sensorData);
                code = ResponseCode.CREATED;
            }catch (Exception e) {
                _Logger.warning(
                        "Failed to handle POST request. Message: " +
                            e.getMessage());
                //BAD_REQUEST(CodeClass.ERROR_RESPONSE, 0)
                code = ResponseCode.BAD_REQUEST;
            }
        }else {
            _Logger.info(
                    "No callback listener for request. Ignoring POST.");
                //CONTINUE(CodeClass.SUCCESS_RESPONSE, 31)
                code = ResponseCode.CONTINUE;
        }
        String msg =
                "Update telemetry data request handled: " + super.getName();
        //do response
        exchange.respond(code, msg);
    }
    @Override
    public void handleDELETE(CoapExchange exchange) {
        // TODO Auto-generated method stub
        super.handleDELETE(exchange);
    }
    /**
     * 这个是用来处理sensor发送到gateway的sensor数据
     * 要put到gateway
     */
    @Override
    public void handlePUT(CoapExchange exchange) {
        //NOT_ACCEPTABLE(CodeClass.ERROR_RESPONSE, 6)
        ResponseCode code = ResponseCode.NOT_ACCEPTABLE;
        
        exchange.accept();
        
        if (this.dataMsgListener != null) {
            try {
                String jsonData = new String(exchange.getRequestPayload());
                
                SensorData sensorData = 
                        DataUtil.getInstance().jsonToSensorData(jsonData);
                
                this.dataMsgListener.handleSensorMessage(
                        ResourceNameEnum.CDA_SENSOR_MSG_RESOURCE, sensorData);
                
                //CHANGED(CodeClass.SUCCESS_RESPONSE, 4)
                code = ResponseCode.CHANGED;
            }catch (Exception e) {
                _Logger.warning(
                        "Failed to handle PUT request. Message: " +
                            e.getMessage());
                //BAD_REQUEST(CodeClass.ERROR_RESPONSE, 0)
                code = ResponseCode.BAD_REQUEST;
            }
        }else {
            _Logger.info(
                "No callback listener for request. Ignoring PUT.");
            //CONTINUE(CodeClass.SUCCESS_RESPONSE, 31)
            code = ResponseCode.CONTINUE;
        }
        
        String msg =
                "Update telemetry data request handled: " + super.getName();
        //do response
        exchange.respond(code, msg);
    }
}
