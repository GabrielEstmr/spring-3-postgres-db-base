package com.challengers.accounts.support.containermocks;

public class MockServerContainerConfiguration extends MockServerContainer {

  private static final String MOCK_SERVER_VERSION = "mockserver-5.6.1";
  private static MockServerContainerConfiguration configuration;

  public MockServerContainerConfiguration() {
    super(MOCK_SERVER_VERSION);
  }

  public static MockServerContainerConfiguration getInstance() {
    if (configuration == null) {
      configuration = new MockServerContainerConfiguration();
    }

    return configuration;
  }

  @Override
  public void start() {
    super.start();
    System.setProperty("MOCKSERVER_IP_ADDRESS", configuration.getContainerIpAddress());
    System.setProperty("MOCKSERVER_PORT", configuration.getMappedPort(MOCK_SERVER_PORT).toString());
  }
}
