package dev.sudarsan.onehelper.config.resolution.input;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ResolutionInput {
    private final Map<String, Integer> ports;
    private final Set<Integer> usedPorts;

    public ResolutionInput(Map<String, Integer> ports) {
        this.ports = ports;
        this.usedPorts = new HashSet<>();
    }

    public Map<String, Integer> getPorts() {
        return ports;
    }

    public Set<Integer> getUsedPorts() {
        return usedPorts;
    }
}
