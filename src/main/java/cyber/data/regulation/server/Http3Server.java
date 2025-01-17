package cyber.data.regulation.server;

public interface Http3Server {
    String beginOrProceed(String sessionId, String message, String appKey);

}
