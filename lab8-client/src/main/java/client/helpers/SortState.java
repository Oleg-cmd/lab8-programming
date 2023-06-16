package client.helpers;

public class SortState {
    private boolean isNameSortAscending = true;
    private boolean isDirectorSortAscending = true;
    private boolean isBirthdaySortAscending = true;
    private boolean isCreationDateSortAscending = true;
    private boolean isHeightSortAscending = true;
    private boolean isDirectorXSortAscending = true;
    private boolean isDirectorYSortAscending = true;
    private boolean isMovieXSortAscending = true;
    private boolean isMovieYSortAscending = true;
    private boolean isOscarsCountSortAscending = true;
    private boolean isGoldenPalmCountSortAscending = true;
    private boolean isTaglineSortAscending = true;
    private boolean isMovieIdSortAscending = true;
    private boolean isPlaceSortAscending = true;
    private boolean isEyeColorSortAscending = true;
    private boolean isMpaaRatingSortAscending = true;

    // ...

    public boolean isMpaaRatingSortAscending() {
        return isMpaaRatingSortAscending;
    }

    public void toggleMpaaRatingSortOrder() {
        isMpaaRatingSortAscending = !isMpaaRatingSortAscending;
    }

    public boolean isEyeColorSortAscending() {
        return isEyeColorSortAscending;
    }

    public void toggleEyeColorSortOrder() {
        isEyeColorSortAscending = !isEyeColorSortAscending;
    }

    public boolean isPlaceSortAscending() {
        return isPlaceSortAscending;
    }

    public void togglePlaceSortOrder() {
        isPlaceSortAscending = !isPlaceSortAscending;
    }

    public boolean isTaglineSortAscending() {
        return isTaglineSortAscending;
    }

    public void toggleTaglineSortOrder() {
        isTaglineSortAscending = !isTaglineSortAscending;
    }

    public boolean isMovieIdSortAscending() {
        return isMovieIdSortAscending;
    }

    public void toggleMovieIdSortOrder() {
        isMovieIdSortAscending = !isMovieIdSortAscending;
    }

    public boolean isHeightSortAscending() {
        return isHeightSortAscending;
    }

    public void toggleHeightSortOrder() {
        isHeightSortAscending = !isHeightSortAscending;
    }

    public boolean isDirectorXSortAscending() {
        return isDirectorXSortAscending;
    }

    public void toggleDirectorXSortOrder() {
        isDirectorXSortAscending = !isDirectorXSortAscending;
    }

    public boolean isDirectorYSortAscending() {
        return isDirectorYSortAscending;
    }

    public void toggleDirectorYSortOrder() {
        isDirectorYSortAscending = !isDirectorYSortAscending;
    }

    public boolean isMovieXSortAscending() {
        return isMovieXSortAscending;
    }

    public void toggleMovieXSortOrder() {
        isMovieXSortAscending = !isMovieXSortAscending;
    }

    public boolean isMovieYSortAscending() {
        return isMovieYSortAscending;
    }

    public void toggleMovieYSortOrder() {
        isMovieYSortAscending = !isMovieYSortAscending;
    }

    public boolean isOscarsCountSortAscending() {
        return isOscarsCountSortAscending;
    }

    public void toggleOscarsCountSortOrder() {
        isOscarsCountSortAscending = !isOscarsCountSortAscending;
    }

    public boolean isGoldenPalmCountSortAscending() {
        return isGoldenPalmCountSortAscending;
    }

    public void toggleGoldenPalmCountSortOrder() {
        isGoldenPalmCountSortAscending = !isGoldenPalmCountSortAscending;
    }

    public boolean isCreationDateSortAscending() {
        return isCreationDateSortAscending;
    }

    public void toggleCreationDateSortOrder() {
        isCreationDateSortAscending = !isCreationDateSortAscending;
    }

    public boolean isBirthdaySortAscending() {
        return isBirthdaySortAscending;
    }

    public void toggleBirthdaySortOrder() {
        isBirthdaySortAscending = !isBirthdaySortAscending;
    }

    public boolean isDirectorSortAscending() {
        return isDirectorSortAscending;
    }

    public void toggleDirectorSortOrder() {
        isDirectorSortAscending = !isDirectorSortAscending;
    }

    public boolean isNameSortAscending() {
        return isNameSortAscending;
    }

    public void toggleNameSortOrder() {
        isNameSortAscending = !isNameSortAscending;
    }
}