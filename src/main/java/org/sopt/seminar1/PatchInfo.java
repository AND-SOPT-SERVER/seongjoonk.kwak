package org.sopt.seminar1;

import java.time.LocalDateTime;

public class PatchInfo {
    private int patchCount;
    private LocalDateTime lastPatchTime;

    public int getPatchCount() {
        return patchCount;
    }

    public void setPatchCount(int patchCount) {
        this.patchCount = patchCount;
    }

    public LocalDateTime getLastPatchTime() {
        return lastPatchTime;
    }

    public void setLastPatchTime(LocalDateTime lastPatchTime) {
        this.lastPatchTime = lastPatchTime;
    }
}
