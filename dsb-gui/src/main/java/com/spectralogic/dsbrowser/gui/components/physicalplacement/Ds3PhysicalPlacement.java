package com.spectralogic.dsbrowser.gui.components.physicalplacement;

import com.spectralogic.ds3client.models.PhysicalPlacement;
import com.spectralogic.ds3client.models.Pool;
import com.spectralogic.ds3client.models.Tape;

import java.util.List;

public class Ds3PhysicalPlacement {
    private final PhysicalPlacement physicalPlacement;
    private final List<Tape> listTapes;
    private final List<Pool> listPools;

    public Ds3PhysicalPlacement() {
        this(null, null, null);
    }

    public Ds3PhysicalPlacement(final PhysicalPlacement physicalPlacement, final List<Tape> listTapes, final List<Pool> listPools) {
        this.physicalPlacement = physicalPlacement;
        this.listPools = listPools;
        this.listTapes = listTapes;
    }

    public PhysicalPlacement getPhysicalPlacement() {
        return physicalPlacement;
    }

    public List<Pool> getListPools() {
        return listPools;
    }

    public List<Tape> getListTapes() {
        return listTapes;
    }
}
