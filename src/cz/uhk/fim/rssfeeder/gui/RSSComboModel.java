package cz.uhk.fim.rssfeeder.gui;

import model.RSSSource;

import javax.swing.*;
import java.util.Vector;

public class RSSComboModel extends DefaultComboBoxModel<RSSSource> {
    public RSSComboModel(Vector<RSSSource> items) {
        super(items);
    }

    @Override
    public RSSSource getSelectedItem() {
        RSSSource selectedJob = (RSSSource) super.getSelectedItem();

        // do something with this job before returning...

        return selectedJob;
    }
}
