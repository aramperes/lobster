package ca.momoperes.lobster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RelationshipTree<T extends RelationshipNode> {

    private final RelationshipProvider<T> provider;

    public RelationshipTree(RelationshipProvider<T> provider) {
        this.provider = provider;
    }

    public List<T> createPath() {
        T lastSelection = null;
        List<T> path = new ArrayList<>();
        double cumulativeCost = 0;
        while (!provider.confirmTermination(lastSelection)) {
            Collection<T> relationships = provider.getRelationships(lastSelection);
            T lowest = null;
            boolean foundCandidate = false;
            for (T relationship : relationships) {
                if (path.contains(relationship)) {
                    continue;
                }
                if (lowest == null) {
                    lowest = relationship;
                } else {
                    double cost = relationship.getHeuristicCost() + relationship.getRelationshipCost() + cumulativeCost;
                    double lowestCost = lowest.getHeuristicCost() + lowest.getRelationshipCost() + cumulativeCost;
                    if (lastSelection == null || lowestCost > cost) {
                        lastSelection = relationship;
                        foundCandidate = true;
                    }
                }
            }
            if (!foundCandidate) {
                return null;
            }
            path.add(lastSelection);
        }
        return path;
    }
}
