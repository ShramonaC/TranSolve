package Modules;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.nearby.messages.Distance;

import java.util.List;

import javax.xml.datatype.Duration;

/**
 * Created by dell on 10-Apr-17.
 */
public class Route {
    public Modules.Distance distance;
    public Modules.Duration duration;
    public String endAddress;
    public LatLng endLocation;
    public String startAddress;
    public LatLng startLocation;

    public List<LatLng> points;
}
