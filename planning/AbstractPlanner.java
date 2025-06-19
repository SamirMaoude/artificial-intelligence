package planning;

public abstract class AbstractPlanner implements Planner{
    protected int visitedNode;
    protected boolean nodeCount;

    protected long startTime = 0;
    protected long endTime = 0;

    @Override
    public void activateNodeCount(boolean activate){
        this.nodeCount = activate;
    }

    @Override
    public int getVisitedNode() {
        return visitedNode;
    }

    @Override
    public long getExecutionTime() {
        return endTime - startTime;
    }
    
}
