package com.github.alexcojocaru.mojo.elasticsearch.v2.step;

import com.github.alexcojocaru.mojo.elasticsearch.v2.ClusterConfiguration;
import com.github.alexcojocaru.mojo.elasticsearch.v2.client.ElasticsearchClient;
import com.github.alexcojocaru.mojo.elasticsearch.v2.client.Monitor;

/**
 * Wait until the ES cluster is up and running.
 * 
 * @author Alex Cojocaru
 */
public class WaitToStartClusterStep
        implements ClusterStep
{
    @Override
    public void execute(ClusterConfiguration config)
    {
    	int firstInstanceHttpPort = config.getInstanceConfigurationList().get(0).getHttpPort();
    	
    	// the instances have already started;
    	// waiting just 10 seconds for them to form the cluster
        int timeout = 10;

        ElasticsearchClient client = new ElasticsearchClient(
                config.getLog(),
                "localhost",
                firstInstanceHttpPort);

        Monitor monitor = new Monitor(client, config.getLog());
        monitor.waitToStartCluster(
                config.getClusterName(),
                config.getInstanceConfigurationList().size(),
                timeout);
    }
}
