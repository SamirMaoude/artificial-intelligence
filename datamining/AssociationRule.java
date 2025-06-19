package datamining;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import modelling.BooleanVariable;

/**
 * Représente une règle d'association composée d'une prémisse et d'une
 * conclusion.
 * Chaque règle possède une fréquence et une confiance
 * utilisée pour évaluer sa pertinence dans un ensemble de données
 */
public class AssociationRule {

    private Set<BooleanVariable> premise;
    private Set<BooleanVariable> conclusion;
    private float frequency;
    private float confidence;

    public AssociationRule(Set<BooleanVariable> premise, Set<BooleanVariable> conclusion, float frequency,
            float confidence) {
        this.premise = premise;
        this.conclusion = conclusion;
        this.frequency = frequency;
        this.confidence = confidence;
    }

    public Set<BooleanVariable> getPremise() {
        return premise;
    }

    public Set<BooleanVariable> getConclusion() {
        return conclusion;
    }

    public float getFrequency() {
        return frequency;
    }

    public float getConfidence() {
        return confidence;
    }

    @Override
    public String toString() {

        List<String> premiseList = premise.stream()
                .map(BooleanVariable::toString)
                .collect(Collectors.toList());

        CharSequence premiseStr = String.join(", ", premiseList);

        List<String> conclusionList = conclusion.stream()
                .map(BooleanVariable::toString)
                .collect(Collectors.toList());

        CharSequence conclusionStr = String.join(", ", conclusionList);

        return premiseStr + " =====> " + conclusionStr + "   [frequency=" + frequency + ", confidence=" + confidence + "]";
    }

}
