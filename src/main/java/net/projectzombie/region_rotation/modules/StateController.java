package net.projectzombie.region_rotation.modules;

import com.sk89q.worldguard.bukkit.WGBukkit;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

/**
 * Created by jb on 8/10/16.
 */
public class StateController
{
    private static StateController STATE_CONTROLLER_SINGLETON = null;

    /** Initializes the StateController. Must be called first in Main. */
    static public void init(final Plugin plugin)
    {
        STATE_CONTROLLER_SINGLETON = new StateController(plugin);
    }

    /** @return Initialized StateController. */
    static public StateController instance() { return STATE_CONTROLLER_SINGLETON; }

    /** @return RegionRotation plugin. */
    static public Plugin plugin()            { return PLUGIN; }

    /** @return WorldGuard plugin. */
    static public Plugin WGplugin()          { return WG_PLUGIN; }

    private final HashMap<String, BaseState> states;
    private static Plugin PLUGIN;
    private static Plugin WG_PLUGIN;

    /**
     * Constructs the singleton StateController and initializes the static plugins.
     * @param plugin Plugin from main initialization.
     */
    private StateController(final Plugin plugin)
    {
        PLUGIN = plugin;
        WG_PLUGIN = WGBukkit.getPlugin();
        this.states = new HashMap<>();
        // TODO: read file from config
    }

    /**
     * Adds a BaseState to the StateController
     * @param state BaseState to add.
     * @return True if successful. False if failed.
     */
    public boolean addBaseState(final BaseState state)
    {
        if (state.isValid())
        {
            states.put(state.getRegionName(), state);
            return true;
        }
        return false;
    }

    /**
     * Removes a BaseState from the StateController.
     * @param baseStateRegionName Name of the BaseState region.
     * @return Removed BaseState if it exists. Null otherwise.
     */
    public BaseState removeBaseState(final String baseStateRegionName)
    {
        return states.remove(baseStateRegionName);
    }

    /**
     * Resets the BaseState by rotating to its backup state.
     * @param baseStateName BaseState to reset.
     * @return True if successful. False if failed.
     */
    public boolean resetBaseState(final String baseStateName,
                                  final boolean broadcast)
    {
        if (states.containsKey(baseStateName))
        {
            final BaseState baseState = states.get(baseStateName);
            final boolean success = baseState.resetState();
            if (broadcast && success)
            {
                baseState.broadcastMessage();
            }
            return success;
        }
        return false;
    }

    /**
     * Rotates an AltState into a BaseState given their respective region names.
     * @param baseStateName WorldGuard region name of BaseState.
     * @param altStateName WorldGuard region name of AltState.
     * @param rotateAir Boolean to copy air to BaseState.
     * @param broadcastMessage Boolean to broadcast AltState message on success.
     * @return True if successful. False if failed.
     */
    public boolean rotateBaseStateBroadcast(final String baseStateName,
                                            final String altStateName,
                                            final boolean rotateAir,
                                            final boolean broadcastMessage)
    {
        if (states.containsKey(baseStateName))
        {
            final BaseState baseState = states.get(baseStateName);
            final boolean success = baseState.rotateState(altStateName, rotateAir);
            if (broadcastMessage && success) {
                baseState.getCurrentState().broadcastMessage();
            }
            return success;
        }
        return false;
    }


}
