package ca.jdr23bc.backgrounds.shapes.tree;

import android.graphics.PointF;

import java.util.ArrayList;

import ca.jdr23bc.backgrounds.shapes.Shape;
import ca.jdr23bc.backgrounds.utils.MathUtils;
import ca.jdr23bc.backgrounds.utils.RandomUtils;


public class Tree extends Shape {
    private static final float BRANCH_CHILD_WIDTH_PERCENTAGE_OF_PARENT = 0.985f;
    private static final float DEFAULT_LEAF_WIDTH = 50;
    private static final float DEFAULT_LEAF_LENGTH = 50;

    private static PointF getUpUnitVector() {
        return new PointF(0, -1);
    }

    private enum GrowState {
        INIT,
        GROW_TRUNK,
        GROW_BRANCHES,
        COMPLETE
    }

    // Init properties
    private TreeDna dna;
    private PointF attractorInitCenter;

    // Growing properties
    private ArrayList<PointF> attractors;
    private Branch trunk;
    private BranchSegment trunkTip;
    private ArrayList<Branch> branches;
    private ArrayList<BranchSegment> growableBranchSegments;
    private int currentBranchSegmentIndex = 0;
    private ArrayList<BranchSegment> branchSegments;
    private ArrayList<Leaf> unreturnedLeaves;

    // Growing state
    private GrowState currentGrowState;

    Tree(PointF topLeft, PointF bottomRight) {
        super(topLeft, bottomRight);
        dna = new TreeDna();
        branches = new ArrayList<>();
        attractorInitCenter = getUpperHalfCenter();
    }

    float getLeafLength() {
        return DEFAULT_LEAF_LENGTH;
    }

    float getLeafWidth() {
        return DEFAULT_LEAF_WIDTH;
    }

    public void init() {
        initTrunk();
        initAttractors();
        currentGrowState = GrowState.INIT;

        growableBranchSegments = new ArrayList<>();
        branchSegments = new ArrayList<>();
        unreturnedLeaves = new ArrayList<>();
    }

    public Boolean isComplete() {
        return currentGrowState == GrowState.COMPLETE;
    }

    public Boolean hasNextBranch() {
        return currentBranchSegmentIndex < branchSegments.size();
    }

    public Boolean hasNextLeaf() {
        return !unreturnedLeaves.isEmpty();
    }

    public BranchSegment nextBranch() {
        return branchSegments.get(currentBranchSegmentIndex++);
    }

    public Leaf nextLeaf() {
        return unreturnedLeaves.remove(0);
    }

    private void initTrunk() {
        // Trunk starts in middle bottom and grows straight up
        trunk = new Branch(dna);
        trunkTip = new BranchSegment(new PointF(getCenter().x, getHeight()),
                getUpUnitVector(), trunk);
        branches.add(trunk);
    }

    private void initAttractors() {
        // Populate attractors
        attractors = new ArrayList<>();
        for (int i = 0; i < dna.getNumberOfAttractors(); i++) {
            attractors.add(getRandomAttractor());
        }
    }

    private float getAttractorRadius() {
        return 0.9f * (Math.min(getWidth(), getHeight()) / 2);
    }

    private PointF getRandomAttractor() {
        float attractorRadius = getAttractorRadius();
        return RandomUtils.getRandomPointInCircle(attractorInitCenter, attractorRadius);
    }

    public void growStep() {
        currentBranchSegmentIndex = 0;
        switch (currentGrowState) {
            case INIT:
                growableBranchSegments.add(trunkTip);
                currentGrowState = GrowState.GROW_TRUNK;
                branchSegments.add(trunkTip);
                break;
            case GROW_TRUNK:
                trunkTip = trunkTip.growOnParent();
                growableBranchSegments.add(trunkTip);
                if (isTrunkWithinRangeOfAttractor()) {
                    currentGrowState = GrowState.GROW_BRANCHES;
                }
                branchSegments.add(trunkTip);
                break;
            case GROW_BRANCHES:
                int prevSize = branchSegments.size();
                growBranches();
                // If growth did not create any new branches then branch growth is complete
                if (branchSegments.size() == prevSize) {
                    currentGrowState = GrowState.COMPLETE;
                }
                break;
        }
        for (Branch branch : branches) {
            branch.updateSegmentWidths();
        }
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
        ArrayList<BranchSegment> segmentsToGrow = new ArrayList<>();
        ArrayList<PointF> connectedAttractors = new ArrayList<>();
        // Add attractors to each branch
        for (PointF attractor : attractors) {
            BranchSegment closestBranchSegment = null;
            double closestDist = 0;
            // Find the closest branch
            for (BranchSegment branchSegment : growableBranchSegments) {
                double dist = MathUtils.getDist(attractor, branchSegment.getTip());
                if (dist < dna.getAttractorConnectedThreshold()) {
                    // Attractor has been reached
                    connectedAttractors.add(attractor);
                    branchSegment.setLeaf(new Leaf(branchSegment.getTip(), this));
                    unreturnedLeaves.add(branchSegment.getLeaf());
                    closestBranchSegment = null;
                    break;
                } else if (dist < getMaxAttractorDistance() &&
                        (closestBranchSegment == null || dist < closestDist)) {
                    // Found a new closest branchSegment
                    closestBranchSegment = branchSegment;
                    closestDist = dist;
                }
            }

            // Add the attractor to the branch
            if (closestBranchSegment != null) {
                closestBranchSegment.addAttractor(attractor);
                if (!segmentsToGrow.contains(closestBranchSegment)) {
                    segmentsToGrow.add(closestBranchSegment);
                }
            }
        }

        // Prune any connected attractors
        attractors.removeAll(connectedAttractors);
        growableBranchSegments = new ArrayList<>();

        // Grow any attracted branches
        for (BranchSegment branchSegment : segmentsToGrow) {
            BranchSegment next;
            if (branchSegment.getNumberOfChildren() == 0) {
                next = branchSegment.growOnParent();
            // If there's more than one child then that is the start of a new branch
            } else {
                Branch newBranch = branchSegment.splitAndGrow();
                branches.add(newBranch);
                next = newBranch.getTip();
            }

            if (next.getWidth() > dna.getBranchTipWidth()) {
                next.setWidth(branchSegment.getWidth() * BRANCH_CHILD_WIDTH_PERCENTAGE_OF_PARENT);
            }
            growableBranchSegments.add(branchSegment);
            growableBranchSegments.add(next);
            branchSegments.add(next);
        }
    }

    private float getMaxAttractorDistance() {
        return Math.min(getWidth(), getHeight());
    }
}
