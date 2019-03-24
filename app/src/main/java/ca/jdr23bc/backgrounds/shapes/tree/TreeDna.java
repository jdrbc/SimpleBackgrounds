package ca.jdr23bc.backgrounds.shapes.tree;

import ca.jdr23bc.backgrounds.utils.RandomUtils;

class TreeDna {
    private static final float DEFAULT_ATTRACTOR_CONNECTED_THRESHOLD = 25f;
    private static final float DEFAULT_BRANCH_TIP_WIDTH = 5f;
    private static final float DEFAULT_BRANCH_SEGMENT_LENGTH = 10f;
    private static final int DEFAULT_NUMBER_OF_ATTRACTORS = 200;
    private static final float DEFAULT_CHILD_WIDTH_TO_PARENT_WIDTH_RATIO = 1f;

    private final float attractorConnectedThreshold;
    private final float branchTipWidth;
    private final float branchSegmentLength;
    private final int numberOfAttractors;
    private final float childWidthToParentWidthRatio;
    private final int segNumberDivisor;

    TreeDna() {
        attractorConnectedThreshold = DEFAULT_ATTRACTOR_CONNECTED_THRESHOLD;
        branchTipWidth = DEFAULT_BRANCH_TIP_WIDTH;
        branchSegmentLength = RandomUtils.getRandomFloatInRange(10f, 20f);
        numberOfAttractors = RandomUtils.getRandomIntInRange(100, 200);
        childWidthToParentWidthRatio = RandomUtils.getRandomFloatInRange(0.5f, 1);
        segNumberDivisor = RandomUtils.getRandomIntInRange(5, 25);
    }

    /**
     * @param parentSegmentWidth The width of a branch segment's parent
     * @param segNumber The number of the segment in the branch
     * @return The width of a segment
     */
    float getBranchSegmentWidth(float parentSegmentWidth, Integer segNumber) {
        return parentSegmentWidth + (float) (Math.log1p(segNumber) / segNumberDivisor);
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

    float getAttractorConnectedThreshold() {
        return attractorConnectedThreshold;
    }
}
