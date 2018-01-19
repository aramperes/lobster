package ca.momoperes.lobster;

import java.util.Collection;

public interface RelationshipProvider<T extends RelationshipNode> {

    Collection<T> getRelationships(T node);

    boolean confirmTermination(T node);
}
