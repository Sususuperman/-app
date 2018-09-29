package me.iwf.photopicker.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.entity.Photo;
import me.iwf.photopicker.entity.PhotoDirectory;
import me.iwf.photopicker.event.Selectable;

public abstract class SelectableAdapter<VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> implements Selectable {

    private static final String TAG = SelectableAdapter.class.getSimpleName();

    protected List<PhotoDirectory> photoDirectories;
    protected List<Photo> selectedPhotos;

    public int currentDirectoryIndex = 0;


    public SelectableAdapter() {
        photoDirectories = new ArrayList<>();
        selectedPhotos = new ArrayList<>();
    }


    /**
     * Indicates if the item at position where is selected
     *
     * @param photo Photo of the item to check
     * @return true if the item is selected, false otherwise
     */
    @Override
    public boolean isSelected(Photo photo) {
        return contains(photo);
    }

    public boolean contains(Photo photo){
        List<Photo> list = getSelectedPhotos();
        if(list != null){
            int size = list.size();
            for (int i = 0; i < size; i++) {
                Photo p = list.get(i);
                if(p.getPath().equals(photo.getPath())){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isEnabled(Photo photo) {
        return photo.getStatus() == Photo.Enabled;
    }

    public boolean isUpload(Photo photo){
        int size = selectedPhotos.size();
        for (int i = 0; i < size; i++) {
            Photo p = selectedPhotos.get(i);
            if(p.equals(photo)){
                return isEnabled(p);
            }
        }
        return false;
    }

    /**
     * Toggle the selection status of the item at a given position
     *
     * @param photo Photo of the item to toggle the selection status for
     */
    @Override
    public void toggleSelection(Photo photo) {
        if (contains(photo)) {
//            selectedPhotos.remove(photo);
            removeForPath(photo);
        } else {
            selectedPhotos.add(photo);
        }
    }


    public void removeForPath(Photo photo){
        int size = selectedPhotos.size();
        int position = 0;
        for (int i = 0; i < size; i++) {
            Photo p = selectedPhotos.get(i);
            if(photo.equals(p)){
                position = i;
            }
        }
        selectedPhotos.remove(position);
    }

    /**
     * Clear the selection status for all items
     */
    @Override
    public void clearSelection() {
        selectedPhotos.clear();
    }


    /**
     * Count the selected items
     *
     * @return Selected items count
     */
    @Override
    public int getSelectedItemCount() {
        return selectedPhotos.size();
    }


    public void setCurrentDirectoryIndex(int currentDirectoryIndex) {
        this.currentDirectoryIndex = currentDirectoryIndex;
    }


    public List<Photo> getCurrentPhotos() {
        return photoDirectories.get(currentDirectoryIndex).getPhotos();
    }


    public List<String> getCurrentPhotoPaths() {
        List<String> currentPhotoPaths = new ArrayList<>(getCurrentPhotos().size());
        for (Photo photo : getCurrentPhotos()) {
            currentPhotoPaths.add(photo.getPath());
        }
        return currentPhotoPaths;
    }


    public List<Photo> getSelectedPhotos() {
        return selectedPhotos;
    }

}