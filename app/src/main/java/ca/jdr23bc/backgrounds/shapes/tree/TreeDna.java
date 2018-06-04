package ca.jdr23bc.backgrounds.shapes.tree;

class TreeDna {
    private static final float DEFAULT_ATTRACTOR_CONNECTED_THRESHOLD = 25f;
    private static final float DEFAULT_TRUNK_MAX_WIDTH = 120f;
    private static final float DEFAULT_BRANCH_TIP_WIDTH = 5f;
    private static final float DEFAULT_BRANCH_WIDTH_GROWTH_RATE = 0.001f; // For each unit of length the width increases by this much
    private static final float DEFAULT_BRANCH_SEGMENT_LENGTH = 10f;
    private static final int DEFAULT_NUMBER_OF_ATTRACTORS = 200;
    private static final float DEFAULT_CHILD_WIDTH_TO_PARENT_WIDTH_RATIO = 0.5f;

    private float trunkMaxWidth;
    private float attractorConnectedThreshold;
    private float branchTipWidth;
    private float branchWidthGrowthRate;
    private float branchSegmentLength;
    private int numberOfAttractors;
    private float childWidthToParentWidthRatio;

    TreeDna() {
        trunkMaxWidth = DEFAULT_TRUNK_MAX_WIDTH;
        attractorConnectedThreshold = DEFAULT_ATTRACTOR_CONNECTED_THRESHOLD;
        branchTipWidth = DEFAULT_BRANCH_TIP_WIDTH;
        branchWidthGrowthRate = DEFAULT_BRANCH_WIDTH_GROWTH_RATE;
        branchSegmentLength = DEFAULT_BRANCH_SEGMENT_LENGTH;
        numberOfAttractors = DEFAULT_NUMBER_OF_ATTRACTORS;
        childWidthToParentWidthRatio = DEFAULT_CHILD_WIDTH_TO_PARENT_WIDTH_RATIO;
    }

    float getBranchWidthGrowthRate() {
        return branchWidthGrowthRate;
    }

    float getChildWidthToParentWidthRatio() {
        return childWidthToParentWidthRatio;
    }

    float getNumberOfAttractors() {
        return numberOfAttractors;
    }

    float getBranchSegmentLength() {
        return branchSegmentLength;
    }

    float getBranchTipWidth() {
        return branchTipWidth;
    }

    float getTrunkMaxWidth() {
        return trunkMaxWidth;
    }

    float getAttractorConnectedThreshold() {
        return attractorConnectedThreshold;
    }
}
