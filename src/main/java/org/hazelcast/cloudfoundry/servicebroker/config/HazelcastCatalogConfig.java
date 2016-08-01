/*
 * Copyright (c) 2008-2016, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.hazelcast.cloudfoundry.servicebroker.config;

import org.cloudfoundry.community.servicebroker.model.Catalog;
import org.cloudfoundry.community.servicebroker.model.Plan;
import org.cloudfoundry.community.servicebroker.model.ServiceDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

/**
 * Catalog Configuration Bean
 */
@Configuration
public class HazelcastCatalogConfig {

    @Bean
    public Catalog catalog() {
        List list = new ArrayList();
        list.add(new ServiceDefinition(
                        "Hazelcast123",
                        "Hazelcast-name",
                        "A simple Hazelcast implementation",
                        true,
                        false,
                        Arrays.asList(
                                new Plan("hazelcast-plan-123",
                                        "default",
                                        "This is a default Hazelcast plan.  All services are created equally.",
                                        getPlanMetadata(), true)),
                        Arrays.asList("hazelcast456", "document"),
                        getServiceDefinitionMetadata(),
                        null,
                        null));

        Map<String, Object> planMetadata = new HashMap<String, Object>();
        planMetadata.put("plan-metadata", "plan-metadata-description");
        List<Plan> plans = Collections.singletonList(new Plan("hazelcast-free-789", "hazelcast-free-plan", "'Free Hazelcast plan!'",
                planMetadata, true));
        list.add(new ServiceDefinition("hazelcast-free-id", "hazelcast-free-name", "Free Hazelcast ", true, true, plans,
                Collections.singletonList("hazelcast"), null, null, null));
        return new Catalog(list);
    }


/* Used by Pivotal CF console */

    private Map<String, Object> getServiceDefinitionMetadata() {
        Map<String, Object> sdMetadata = new HashMap<String, Object>();
        sdMetadata.put("displayName", "Hazelcast");
        sdMetadata.put("longDescription", "Hazelcast Service");
        sdMetadata.put("providerDisplayName", "Pivotal");
        sdMetadata.put("documentationUrl", "http://www.hazelcast.org");
        sdMetadata.put("supportUrl", "http://www.hazelcast.org");
        return sdMetadata;
    }

    private Map<String, Object> getPlanMetadata() {
        Map<String, Object> planMetadata = new HashMap<String, Object>();
        planMetadata.put("costs", getCosts());
        planMetadata.put("bullets", getBullets());
        return planMetadata;
    }

    private List<Map<String, Object>> getCosts() {
        Map<String, Object> costsMap = new HashMap<String, Object>();

        Map<String, Object> amount = new HashMap<String, Object>();
        amount.put("usd", new Double(0));

        costsMap.put("amount", amount);
        costsMap.put("unit", "MONTHLY");

        return Arrays.asList(costsMap);
    }

    private List<String> getBullets() {
        return Arrays.asList("Shared Hazelcast cluster",
                "100 MB Storage (not enforced)",
                "40 concurrent connections (not enforced)");
    }
}
