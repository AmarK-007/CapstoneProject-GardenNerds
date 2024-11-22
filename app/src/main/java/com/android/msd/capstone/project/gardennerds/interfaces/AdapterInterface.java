package com.android.msd.capstone.project.gardennerds.interfaces;

/**
 * Interface for Adapter
 */
public interface AdapterInterface<T> {
    /**
     * onItemSelected method
     *
     * @param data
     * @param position
     * @return
     */
    void onItemSelected(T data, int position);

    /**
     * onItemRemoved method
     *
     * @return
     */
    void onItemRemoved();
}
