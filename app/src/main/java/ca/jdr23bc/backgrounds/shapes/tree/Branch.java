package ca.jdr23bc.backgrounds.shapes.tree;

import java.util.ArrayList;

class Branch {
    private final TreeDna dna;
    private final ArrayList<BranchSegment> segments;
    private BranchSegment parentSegment;
    private int segmentCountOnLastUpdate;
    private float parentSegmentWidthOnLastUpdate;

    Branch(TreeDna dna) {
        this.dna = dna;
        segments = new ArrayList<>();
        segmentCountOnLastUpdate = 0;
    }

    Branch(BranchSegment parentBranchSegment) {
        this(parentBranchSegment.getParentBranch().getDna());
        this.parentSegment = parentBranchSegment;
    }

    void addSegment(BranchSegment segment) {
        segments.add(segment);
    }

    private TreeDna getDna() {
        return dna;
    }

    BranchSegment getTip() {
        return segments.get(segments.size() - 1);
    }

    void updateSegmentWidths() {
        // If no new segments have been added & parent segment hasn't changed then widths will be the same
        if (segmentCountOnLastUpdate == segments.size() &&
                (!isTrunk() && parentSegmentWidthOnLastUpdate == parentSegment.getWidth())) {
            return;
        } else {
            segmentCountOnLastUpdate = segments.size();
            if (!isTrunk()) {
                parentSegmentWidthOnLastUpdate = parentSegment.getWidth();
            }
        }
        Integer segNumber = 0;
        float maxWidth = 0;
        if (!isTrunk()) {
            maxWidth = parentSegment.getWidth() * dna.getChildWidthToParentWidthRatio();
        }
        // Go from tip to base
        Boolean maxWidthReached = false;
        float currentWidth = dna.getBranchTipWidth();
        for (Integer i = segments.size() - 1; i >= 0; i--) {
            BranchSegment segment = segments.get(i);
            if (isTrunk() || !maxWidthReached) {
                if (currentWidth > segment.getWidth()) {
                    segment.setWidth(currentWidth);
                }
                currentWidth = dna.getBranchSegmentWidth(currentWidth, segNumber++);
                if (!isTrunk() && currentWidth > maxWidth) {
                    maxWidthReached = true;
                }
            } else {
                segment.setWidth(maxWidth);
            }
        }
    }

    private boolean isTrunk() {
        return parentSegment == null;
    }
}
