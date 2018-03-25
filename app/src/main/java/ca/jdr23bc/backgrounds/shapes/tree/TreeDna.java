package ca.jdr23bc.backgrounds.shapes.tree;

class TreeDna {
    private static final float DEFAULT_ATTRACTOR_CONNECTED_THRESHOLD = 25f;
    private static final float DEFAULT_TRUNK_WIDTH = 80f;
    private static final float DEFAULT_BRANCH_TIP_WIDTH = 5f;
    private static final float DEFAULT_BRANCH_WIDTH_GROWTH_RATE = 0.0015f; // For each unit of length the width increases by this much
    private static final float DEFAULT_BRANCH_SEGMENT_LENGTH = 10f;
    private static final int DEFAULT_NUMBER_OF_ATTRACTORS = 100;

    private float trunkMaxWidth;
    private float attractorConnectedThreshold;
    private float branchTipWidth;
    private float branchWidthGrowthRate;
    private float branchSegmentLength;
    private int numberOfAttractors;

    TreeDna() {
        trunkMaxWidth = DEFAULT_TRUNK_WIDTH;
        attractorConnectedThreshold = DEFAULT_ATTRACTOR_CONNECTED_THRESHOLD;
        branchTipWidth = DEFAULT_BRANCH_TIP_WIDTH;
        branchWidthGrowthRate = DEFAULT_BRANCH_WIDTH_GROWTH_RATE;
        branchSegmentLength = DEFAULT_BRANCH_SEGMENT_LENGTH;
        numberOfAttractors = DEFAULT_NUMBER_OF_ATTRACTORS;
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

    /**
     * @param distanceFromTip The distance of the branch segment from the tip of the branch
     * @return the width of a branch segment given that it is the given distance from the tip
     */
    float getBranchSegmentWidth(float distanceFromTip) {
        return Math.max(trunkMaxWidth, branchTipWidth + (distanceFromTip * branchWidthGrowthRate));
    }
}
