package com.ubs.sample.card.validator.app.assembly;

import com.ubs.sample.card.ports.assembly.PortsAssembly;
import com.ubs.sample.card.services.assembly.ServicesAssembly;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Top Level assembly for Application
 */
@Configuration
@Import({
        // Module Assembly Imports
        PortsAssembly.class,
        ServicesAssembly.class
})
public class ProgramAssembly {
}
