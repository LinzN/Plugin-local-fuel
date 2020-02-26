/*
 * Copyright (C) 2020. Niklas Linz - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 * You should have received a copy of the LGPLv3 license with
 * this file. If not, please write to: niklas.linz@enigmar.de
 *
 */

package de.linzn.localFuel;


import de.azcore.azcoreRuntime.AZCoreRuntimeApp;
import de.azcore.azcoreRuntime.modules.pluginModule.AZPlugin;
import de.azcore.azcoreRuntime.taskManagment.operations.OperationRegister;
import de.linzn.localFuel.data.CheckPriceCallback;
import de.linzn.localFuel.data.FuelCommand;
import de.linzn.localFuel.data.FuelData;
import de.linzn.localFuel.data.FuelOperations;


public class LocalFuelPlugin extends AZPlugin {

    public static LocalFuelPlugin localFuelPlugin;
    public FuelData fuelData;

    public LocalFuelPlugin() {
        localFuelPlugin = this;
    }

    @Override
    public void onEnable() {
        this.fuelData = new FuelData();
        this.getDefaultConfig().get("tanken.apiKey", "xxxxxxxxxxxxxxx");
        this.getDefaultConfig().get("tanken.data.lat", 1.1);
        this.getDefaultConfig().get("tanken.data.lng", 1.1);
        this.getDefaultConfig().get("tanken.data.range", 10);
        this.getDefaultConfig().get("tanken.data.type", "e5");
        this.getDefaultConfig().save();
        OperationRegister.addOperation("update_fuel_data", FuelOperations.update_fuel_data);
        AZCoreRuntimeApp.getInstance().getCommandModule().registerCommand("fuel", new FuelCommand());
        AZCoreRuntimeApp.getInstance().getCallBackService().registerCallbackListener(new CheckPriceCallback(), this);
    }

    @Override
    public void onDisable() {
        AZCoreRuntimeApp.getInstance().getCommandModule().unregisterCommand("fuel");
    }
}
