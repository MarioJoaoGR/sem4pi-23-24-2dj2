package jobs4u.server.deamon.followup.server;

public abstract class FollowUpRequest {

    protected final static byte DATA1_LEN_L = 1;
    protected final static byte DATA1_LEN_M = 2;
    protected final static byte DATA2_LEN_L = 1;
    protected final static byte DATA_LEN_M = 2;

    protected final static byte VERSION = 1;

    protected final static byte COMM_TEST = 0;
    protected final static byte DISCONN = 1;
    protected final static byte ACK = 2;
    protected final static byte ERR = 3;
    protected final static byte AUTH = 4;
    protected final static byte JOB_APPLICATION_REQUEST = 5;
    protected final static byte GET_JOB_OPENINGS = 6;
    protected final static byte GET_CUSTOMER =7;

    protected final static byte GET_NOTIFICATIONS_READ = 8;

    protected final static byte GET_NOTIFICATIONS_NOT_READ = 9;

    protected final static byte MAKE_NOTIFICATION_READ = 10;

    protected final static byte DATA = 50;




    protected final byte[] request;
    protected final Object controller;

    protected FollowUpRequest(final Object controller, final byte[] request) {
        this.controller = controller;
        this.request = request;
    }

    public abstract byte[] execute();



}
