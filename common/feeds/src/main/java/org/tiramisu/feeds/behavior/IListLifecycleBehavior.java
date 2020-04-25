package org.tiramisu.feeds.behavior;

import org.tiramisu.feeds.data.IChannelModel;
import org.tiramisu.feeds.lifecycle.IListViewHolderLifecycle;
import org.tiramisu.feeds.lifecycle.IListViewLifecycle;

public interface IListLifecycleBehavior extends
        IBehavior,
        IListViewLifecycle,
        IListViewHolderLifecycle,
        IBusinessBehavior
{
    void bindChannelModel(IChannelModel channelModel);
}
