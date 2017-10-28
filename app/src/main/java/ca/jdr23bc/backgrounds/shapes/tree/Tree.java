package ca.jdr23bc.backgrounds.shapes.tree;

import android.graphics.PointF;

import java.util.ArrayList;

import ca.jdr23bc.backgrounds.shapes.Shape;
import ca.jdr23bc.backgrounds.utils.MathUtils;
import ca.jdr23bc.backgrounds.utils.RandomUtils;


public class Tree extends Shape {
    private static final float ATTRACTOR_CONNECTED_THRESHOLD = 25f;

    private static PointF getUpUnitVector() {
        return new PointF(0, -1);
    }

    private enum GrowState {
        INIT,
        GROW_TRUNK,
        GROW_BRANCHES,
        GROW_LEAVES
    }

    // Init properties
    private float branchLength;
    private float branchWidth;
    private float leafLength;
    private float leafWidth;
    private int numberOfAttractors;
    private PointF attractorInitCenter;

    // Growing properties
    private ArrayList<PointF> attractors;
    private Branch trunkTip;
    private ArrayList<Branch> growableBranches;
    private ArrayList<Branch> unreturnedBranches;
    private ArrayList<Branch> endBranches;

    // Growing state
    private GrowState currentGrowState;

    public Tree(PointF topLeft, PointF bottomRight) {
        super(topLeft, bottomRight);
        branchLength = 10;
        branchWidth = 20;
        leafLength = 100;
        leafWidth = 50;
        numberOfAttractors = 100;
        attractorInitCenter = RandomUtils.getRandomPointInRect(getTopLeft(), getCenterRight());
    }

    public float getLeafLength() {
        return leafLength;
    }

    public float getLeafWidth() {
        return leafWidth;
    }

    public void init() {
        initTrunk();
        initAttractors();
        currentGrowState = GrowState.INIT;

        growableBranches = new ArrayList<>();
        unreturnedBranches = new ArrayList<>();
        endBranches = new ArrayList<>();
    }

    public Boolean hasNextBranch() {
        // return true if branches are not all returned
        return (currentGrowState == GrowState.INIT || currentGrowState == GrowState.GROW_TRUNK ||
                currentGrowState == GrowState.GROW_BRANCHES);
    }

    public Boolean hasNextLeaf() {
        return endBranches.size() > 0;
    }

    public Branch nextBranch() {
        switch (currentGrowState) {
            case INIT:
                growableBranches.add(trunkTip);
                currentGrowState = GrowState.GROW_TRUNK;
                return trunkTip;
            case GROW_TRUNK:
                trunkTip = trunkTip.grow();
                growableBranches.add(trunkTip);
                if (isTrunkWithinRangeOfAttractor()) {
                    currentGrowState = GrowState.GROW_BRANCHES;
                }
                return trunkTip;
            case GROW_BRANCHES:
                if (unreturnedBranches.size() > 1) {
                    return unreturnedBranches.remove(0);
                } else {
                    int prevSize = unreturnedBranches.size();
                    growBranches();
                    // If growth did not create any new branches then branch growth is complete
                    if (unreturnedBranches.size() == prevSize) {
                        currentGrowState = GrowState.GROW_LEAVES;
                    }
                    if (unreturnedBranches.size() != 0) {
                        return unreturnedBranches.remove(0);
                    }
                }
            default:
                return trunkTip;
        }
    }

    public Leaf nextLeaf() {
        if (endBranches.size() >= 1) {
            return new Leaf(RandomUtils.removeRandomElement(endBranches).getTip(), this);
        } else {
            return null;
        }
    }

    private float getHalfWidth() {
        return getWidth() / 2;
    }

    private void initTrunk() {
        // Trunk starts in middle bottom and grows straight up
        trunkTip = new Branch(new PointF(getHalfWidth(), getHeight()), getUpUnitVector(), branchLength, branchWidth);
    }

    private void initAttractors() {
        // Populate attractors
        attractors = new ArrayList<>();
        for (int i = 0; i < numberOfAttractors; i++) {
            attractors.add(getRandomAttractor());
        }
    }

    private PointF getRandomAttractor() {
        float attractorRadius = 3 * Math.min(getWidth(), getHeight()) / 4;
        return RandomUtils.getRandomPointInCircle(attractorInitCenter, attractorRadius);
    }

    private Boolean isTrunkWithinRangeOfAttractor() {
        for (PointF attractor : attractors) {
            Double dist = MathUtils.getDist(attractor, trunkTip.getTip());
            if (dist < getMaxAttractorDistance()) {
                return true;
            }
        }
        return false;
    }

    private void growBranches() {
        ArrayList<Branch> branchesToGrow = new ArrayList<>();
        ArrayList<PointF> connectedAttractors = new ArrayList<>();
        // Add attractors to each branch
        for (PointF attractor : attractors) {
            Branch closestBranch = null;
            double closestDist = 0;
            // Find the closest branch
            for (Branch branch : growableBranches) {
                double dist = MathUtils.getDist(attractor, branch.getTip());
                if (dist < ATTRACTOR_CONNECTED_THRESHOLD) {
                    // Attractor has been reached
                    connectedAttractors.add(attractor);
                    if (!endBranches.contains(branch)) {
                        endBranches.add(branch);
                    }
                    closestBranch = null;
                    break;
                } else if (dist < getMaxAttractorDistance() &&
                        (closestBranch == null || dist < closestDist)) {
                    // Found a new closest branch
                    closestBranch = branch;
                    closestDist = dist;
                }
            }

            // Add the attractor to the branch
            if (closestBranch != null) {
                closestBranch.addAttractor(attractor);
                if (!branchesToGrow.contains(closestBranch)) {
                    branchesToGrow.add(closestBranch);
                }
            }
        }

        // Prune any connected attractors
        attractors.removeAll(connectedAttractors);
        growableBranches = new ArrayList<>();

        // Grow any attracted branches
        for (Branch branch : branchesToGrow) {
            Branch next = branch.grow();
            growableBranches.add(branch);
            growableBranches.add(next);
            unreturnedBranches.add(next);
        }
    }

    private float getMaxAttractorDistance() {
        return Math.min(getWidth(), getHeight());
    }
}
