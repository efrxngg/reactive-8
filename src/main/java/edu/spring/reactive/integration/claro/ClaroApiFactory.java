package edu.spring.reactive.integration.claro;

import edu.spring.reactive.integration.claro.interfaces.ClaroApiClient;
import edu.spring.reactive.integration.claro.utils.MicroEISClient;
import edu.spring.reactive.integration.claro.utils.MicroGatewayClient;
import edu.spring.reactive.integration.claro.utils.MicroPersistorClient;
import edu.spring.reactive.integration.claro.utils.MicroType;
import org.springframework.stereotype.Component;

@Component
public class ClaroApiFactory {

    private final MicroEISClient microEISClient;
    private final MicroGatewayClient microGatewayClient;
    private final MicroPersistorClient microPersistorClient;

    public ClaroApiFactory(
            MicroGatewayClient microGatewayClient,
            MicroPersistorClient microPersistorClient,
            MicroEISClient microEISClient) {
        this.microGatewayClient = microGatewayClient;
        this.microPersistorClient = microPersistorClient;
        this.microEISClient = microEISClient;
    }

    public ClaroApiClient getInstance(MicroType microType) throws IllegalAccessException {
        switch (microType) {
            case GATEWAY:
                return microGatewayClient;
            case PERSISTOR:
                return microPersistorClient;
            case EIS:
                return microEISClient;
            default:
                throw new IllegalAccessException("Invalid Microtype");
        }
    }

}
