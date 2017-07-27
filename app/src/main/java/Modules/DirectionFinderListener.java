package Modules;

import java.util.List;

/**
 * Created by dell on 10-Apr-17.
 */
public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
