package ca.jdr23bc.backgrounds.shapes.tree;

import java.util.ArrayList;

class Branch {
    TreeDna dna;
    Branch parent;
    ArrayList<BranchSegment> segments;
    BranchSegment parentSegment;
    private int segmentCountOnLastUpdate;

    Branch(TreeDna dna) {
        this.dna = dna;
        segments = new ArrayList<>();
        segmentCountOnLastUpdate = 0;
    }

    Branch(BranchSegment parentBranchSegment) {
        this(parentBranchSegment.getParentBranch().getDna());
        this.parent = parentBranchSegment.getParentBranch();
        this.parentSegment = parentBranchSegment;
    }

    Branch addSegment(BranchSegment segment) {
        segments.add(segment);
        return this;
    }

    TreeDna getDna() {
        return dna;
    }

    BranchSegment getTip() {
        return segments.get(segments.size() - 1);
    }

    void updateSegmentWidths() {
        // If no new segments have been added widths will be the same
        if (segmentCountOnLastUpdate == segments.size()) {
            return;
        } else {
            segmentCountOnLastUpdate = segments.size();
        }
        float distFromTip = 0;
        float currentWidth = dna.getBranchTipWidth();
        float maxWidth = dna.getTrunkMaxWidth();
        if (parentSegment != null) {
            maxWidth = Math.min(maxWidth, parentSegment.getWidth() * dna.getChildWidthToParentWidthRatio());
        }
        Boolean maxWidthReached = false;
        // Go from tip to base
        for (Integer i = segments.size() - 1; i >= 0; i--) {
            BranchSegment segment = segments.get(i);
            if (!maxWidthReached) {
                segment.setWidth(currentWidth);
                distFromTip += segment.getLength();
                currentWidth += distFromTip * dna.getBranchWidthGrowthRate();
                if (currentWidth > maxWidth) {
                    maxWidthReached = true;
                }
            } else {
                segment.setWidth(maxWidth);
            }
        }
    }
}
