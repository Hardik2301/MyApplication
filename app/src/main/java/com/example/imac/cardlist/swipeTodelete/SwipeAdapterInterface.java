package com.example.imac.cardlist.swipeTodelete;

import java.util.List;

public interface SwipeAdapterInterface {

    int getSwipeLayoutResourceId(int position);

    void notifyDatasetChanged();

    public interface SwipeItemMangerInterface {

        void openItem(int position);

        void closeItem(int position);

        void closeAllExcept(SwipeLayout layout);

        void closeAllItems();

        List<Integer> getOpenItems();

        List<SwipeLayout> getOpenLayouts();

        void removeShownLayouts(SwipeLayout layout);

        boolean isOpen(int position);

        RecyclerSwipeAdapter.Mode getMode();

        void setMode(RecyclerSwipeAdapter.Mode mode);
    }
}
