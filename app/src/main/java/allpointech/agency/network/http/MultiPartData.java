package allpointech.agency.network.http;

public class MultiPartData {
    public static final int HEADER = 0;
    public static final int FORM = 1;
    public static final int FILE = 2;
    public static final int FORM_ONCE = 3;
    /**
     * 0: header, 1: form, 2: file
     */
    public int type;
    public String name;
    public String value;

    /**
     * @param type  0: header, 1: form, 2: file
     * @param name  key
     * @param value value
     * @Desc : Constructor of MultiPartData.java class
     */
    public MultiPartData(int type, String name, String value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return "MultiPartData [type=" + type + ", name=" + name + ", value="
                + value + "]";
    }

}
