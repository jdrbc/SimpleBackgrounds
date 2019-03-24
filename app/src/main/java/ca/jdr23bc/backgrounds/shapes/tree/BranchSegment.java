package ca.jdr23bc.backgrounds.shapes.tree;

import android.graphics.PointF;

import java.util.ArrayList;

import ca.jdr23bc.backgrounds.utils.MathUtils;

public class BranchSegment {
    private static final float DEFAULT_BRANCH_SEGMENT_LENGTH = 10;
    private static final float DEFAULT_BRANCH_SEGMENT_WIDTH = 10;
    private float width;
    private final PointF tip;
    private final PointF base;
    private final PointF dir;
    private Leaf leaf;
    private Integer numChildren;
    private final Branch parentBranch;

    // List of attracting points
    private final ArrayList<PointF> curAttractors = new ArrayList<>();

    private BranchSegment(PointF tip, PointF base, PointF dir, Branch parentBranch) {
        this.tip = tip;
        this.base = base;
        this.dir = dir;
        this.parentBranch = parentBranch;
        this.width = DEFAULT_BRANCH_SEGMENT_WIDTH;
        this.numChildren = 0;
        parentBranch.addSegment(this);
    }

    // Root constructor - root has length of 0
    BranchSegment(PointF tip, PointF dir, Branch trunk) {
        this(tip, tip, dir, trunk);
    }

    /**
     * Create a new branch segment and add the new segment to the branch parent
     * @precondition This segment has no children segments
     * @return The branches
     */
    public BranchSegment growOnParent() {
        if (numChildren != 0) {
            throw new IllegalStateException("Cannot have any child segments");
        }
        return grow(parentBranch);
    }

    /**
     * Create a new branch segment, add it to a new branch, and return the new branch
     * @precondition This segment has at least one child segment
     * @return The new branch
     */
    Branch splitAndGrow() {
        if (numChildren == 0) {
            throw new IllegalStateException("Must have at least one child segment");
        }
        Branch newBranch = new Branch(this);
        grow(newBranch);
        return newBranch;
    }

    /**
     * @param branchToAddTo The parent branch of the new segment
     * @return The new branch segment
     */
    private BranchSegment grow(Branch branchToAddTo) {
        numChildren++;
        PointF attractedDir = new PointF(dir.x, dir.y);
        // Two attractors leads to risk of branch trapping
        if (curAttractors.size() == 2) {
            curAttractors.remove(0);
        }
        for (PointF attractor : curAttractors) {
            PointF attractorToTip = MathUtils.sub(attractor, tip);
            PointF attractorToTipUnitVector = MathUtils.normalize(attractorToTip);
            attractedDir = MathUtils.add(attractedDir, attractorToTipUnitVector);
        }
        attractedDir = MathUtils.normalize(attractedDir);
        curAttractors.clear();
        PointF nextBranchTip = MathUtils.add(tip, MathUtils.mult(attractedDir, DEFAULT_BRANCH_SEGMENT_LENGTH));
        return new BranchSegment(nextBranchTip, tip, attractedDir, branchToAddTo);
    }

    Branch getParentBranch() {
        return parentBranch;
    }

    Integer getNumberOfChildren() {
        return numChildren;
    }

    public PointF getTip() {
        return tip;
    }

    public PointF getBase() {
        return base;
    }

    public float getWidth() {
        return width;
    }

    public float getLength() {
        return MathUtils.getDistF(base, tip);
    }

    public Boolean hasLeaf() {
        return leaf != null;
    }

    Leaf getLeaf() {
        return leaf;
    }

    void setLeaf(Leaf leaf) {
        this.leaf = leaf;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    void addAttractor(PointF p) {
        curAttractors.add(p);
    }
}
