package dev.sudarsan.onehelper.config;

import dev.sudarsan.onehelper.validation.common.ValueValidator;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Map;
import java.util.Set;

public class PortResolver implements ConfigResolver {

    @Override
    public boolean supports(ResolutionInput input, String config) {
        Map<String, Integer> ports = input.getPorts();
        if (ValueValidator.isNullOrEmpty(ports)){
            return false;
        }

        return arePortsNeed(config, ports);
    }

    private boolean arePortsNeed(String resultantConfig, Map<String, Integer> ports) {
        for (String portKey : ports.keySet()) {
            String placeHolder = "{{" + portKey + "}}";
            if (resultantConfig.contains(placeHolder)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void resolveConfig(ResolutionInput resolutionInput, Map<String, String> configValueMap) {
        if (resolutionInput.getPorts() == null || resolutionInput.getPorts().isEmpty()) {
            return;
        }

        for (Map.Entry<String, Integer> portEntry : resolutionInput.getPorts().entrySet()) {
            configValueMap.put(portEntry.getKey(), String.valueOf(resolvePort(resolutionInput.getUsedPorts(), portEntry.getValue())));
        }
    }

    private int resolvePort(Set<Integer> usedPorts, int desiredPort) {
        while (!isPortAvailable(usedPorts, desiredPort) && desiredPort < 65535) {
            desiredPort++;
        }
        usedPorts.add(desiredPort);
        return desiredPort;
    }

    private boolean isPortAvailable(Set<Integer> usedPorts, int desiredPort) {
        if (usedPorts.contains(desiredPort)) {
            return false;
        }

        try (ServerSocket socket = new ServerSocket(desiredPort)) {
            socket.setReuseAddress(true);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
