package ser.pipi.piball.net;

import com.esotericsoftware.jsonbeans.Json;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 * Created by ser on 06.03.18.
 */

class CustJsonSerializer<T> extends Serializer<T> {

    final Json json;
    final Class<T> type;

    public CustJsonSerializer(Class<T> type) {
        super(false, false);
        this.json = new Json();
        this.type = type;
    }

    @Override
    public T read(Kryo kryo, Input input, Class type) {
        final String js = input.readString();
        //System.out.println("read: " + js);
        return json.fromJson(this.type,js);
    }

    @Override
    public void write(Kryo kryo, Output output, T object) {
            final String js = json.toJson(object, type);
            //System.out.println("write " + js);
            output.writeString(js);
    }
}
