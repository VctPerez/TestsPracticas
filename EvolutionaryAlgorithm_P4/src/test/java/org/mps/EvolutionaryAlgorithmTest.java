package org.mps;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import org.mps.crossover.CrossoverOperator;
import org.mps.crossover.OnePointCrossover;
import org.mps.mutation.MutationOperator;
import org.mps.mutation.SwapMutation;
import org.mps.selection.SelectionOperator;
import org.mps.selection.TournamentSelection;

import java.lang.reflect.Array;
import java.util.Arrays;


public class EvolutionaryAlgorithmTest {//Trabajo en Grupo de David Bueno Carmona, Victor Perez Armenta y Jose Ángel Bueno Ruiz
    EvolutionaryAlgorithm algorithm;
    SelectionOperator selectionOperator;
    MutationOperator mutationOperator;
    CrossoverOperator crossoverOperator;

    @Nested
    class Constructor{
        @Test
        @DisplayName("Throws exception with null SelectionOperator")
        void constructor_nullSelectionOperator_throwsException(){
            selectionOperator = null;
            mutationOperator = new SwapMutation();
            crossoverOperator = new OnePointCrossover();

            assertThrows(EvolutionaryAlgorithmException.class, ()->{
                algorithm = new EvolutionaryAlgorithm(selectionOperator, mutationOperator, crossoverOperator);
            });
        }

        @Test
        @DisplayName("Throws exception with null MutationOperator")
        void constructor_nullMutationOperator_throwsException() throws EvolutionaryAlgorithmException {
            selectionOperator = new TournamentSelection(1);
            mutationOperator = null;
            crossoverOperator = new OnePointCrossover();

            assertThrows(EvolutionaryAlgorithmException.class, ()->{
                algorithm = new EvolutionaryAlgorithm(selectionOperator, mutationOperator, crossoverOperator);
            });
        }

        @Test
        @DisplayName("Throws exception with null CrossoverOperator ")
        void constructor_nullCrossoverOperator_throwsException() throws EvolutionaryAlgorithmException {
            selectionOperator = new TournamentSelection(1);
            mutationOperator = new SwapMutation();
            crossoverOperator = null;

            assertThrows(EvolutionaryAlgorithmException.class, ()->{
                algorithm = new EvolutionaryAlgorithm(selectionOperator, mutationOperator, crossoverOperator);
            });
        }

        @Test
        @DisplayName("Builds EvolutiyonaryAlgorithm with valid arguments ")
        void constructor_validArguments_buildsEvolutionaryAlgorithm() throws EvolutionaryAlgorithmException {
            selectionOperator = new TournamentSelection(1);
            mutationOperator = new SwapMutation();
            crossoverOperator = new OnePointCrossover();

            assertDoesNotThrow(()->{
                algorithm = new EvolutionaryAlgorithm(selectionOperator, mutationOperator, crossoverOperator);
            });
            assertEquals(selectionOperator, algorithm.getSelectionOperator());
            assertEquals(mutationOperator, algorithm.getMutationOperator());
            assertEquals(crossoverOperator, algorithm.getCrossoverOperator());
        }
    }


    @BeforeEach
    void setup() throws EvolutionaryAlgorithmException {
        selectionOperator = new TournamentSelection(3);
        mutationOperator = new SwapMutation();
        crossoverOperator = new OnePointCrossover();
        algorithm = new EvolutionaryAlgorithm(selectionOperator, mutationOperator, crossoverOperator);
    }

    @Nested
    class Getters{
        @Test
        @DisplayName("getSelectionOperator returns correct operator")
        void getSelectionOperator_returnsCorrectOperator(){
            assertEquals(selectionOperator, algorithm.getSelectionOperator());
        }

        @Test
        @DisplayName("getMutationOperator returns correct operator")
        void getMutationOperator_returnsCorrectOperator(){
            assertEquals(mutationOperator, algorithm.getMutationOperator());
        }

        @Test
        @DisplayName("getCrossoverOperator returns correct operator")
        void getCrossoverOperator_returnsCorrectOperator(){
            assertEquals(crossoverOperator, algorithm.getCrossoverOperator());
        }
    }

    @Nested
    class SetSelectionOperator{
        @Test
        @DisplayName("Throws EvolutionaryAlgorithmException with null operator")
        void setSelectionOperator_nullOperator_throwsEvolutionaryAlgorithmException(){
            assertThrows(EvolutionaryAlgorithmException.class, ()->{
                algorithm.setSelectionOperator(null);
            });
        }

        @Test
        @DisplayName("Changes the algorithm's operator with valid operator")
        void setSelectionOperator_validOperator_changesOperator() throws EvolutionaryAlgorithmException {
            SelectionOperator newSelectionOperator = new TournamentSelection(3);

            algorithm.setSelectionOperator(newSelectionOperator);

            assertEquals(newSelectionOperator, algorithm.getSelectionOperator());
        }
    }

    @Nested
    class SetMutationOperator{
        @Test
        @DisplayName("Throws EvolutionaryAlgorithmException with null operator")
        void setMutationOperator_nullOperator_throwsEvolutionaryAlgorithmException(){
            assertThrows(EvolutionaryAlgorithmException.class, ()->{
                algorithm.setMutationOperator(null);
            });
        }

        @Test
        @DisplayName("Changes the algorithm's operator with valid operator")
        void setMutationOperator_validOperator_changesOperator() throws EvolutionaryAlgorithmException {
            MutationOperator newMutationOperator = new SwapMutation();

            algorithm.setMutationOperator(newMutationOperator);

            assertEquals(newMutationOperator, algorithm.getMutationOperator());
        }
    }

    @Nested
    class SetCrossoverOperator{
        @Test
        @DisplayName("Throws EvolutionaryAlgorithmException with null operator")
        void setCrossoverOperator_nullOperator_throwsEvolutionaryAlgorithmException(){
            assertThrows(EvolutionaryAlgorithmException.class, ()->{
                algorithm.setCrossoverOperator(null);
            });
        }

        @Test
        @DisplayName("Changes the algorithm's operator with valid operator")
        void setSelectionOperator_validOperator_changesOperator() throws EvolutionaryAlgorithmException {
            CrossoverOperator newCrossoverOperator = new OnePointCrossover();

            algorithm.setCrossoverOperator(newCrossoverOperator);

            assertEquals(newCrossoverOperator, algorithm.getCrossoverOperator());
        }
    }

    @Nested
    class Optimize{
        @Test
        @DisplayName("Throws EvolutionaryAlgorithmException with null population")
        void optimize_nullPopulation_throwsEvolutionaryAlgorithmException(){
            assertThrows(EvolutionaryAlgorithmException.class, ()->{
                int[][] optimizedPopulation = algorithm.optimize(null);
            });
        }
        @Test
        @DisplayName("Throws EvolutionaryAlgorithmException with a population with null attributes")
        void optimize_nullAttributesPopulation_throwsEvolutionaryAlgorithmException(){
            int[][] population = {null, null};
            assertThrows(EvolutionaryAlgorithmException.class, ()->{
                int[][] optimizedPopulation = algorithm.optimize(population);
            });
        }
        @Test
        @DisplayName("Throws EvolutionaryAlgorithmException with empty population")
        void optimize_emptyPopulation_throwsEvolutionaryAlgorithmException(){
            int[][] population = {};
            assertThrows(EvolutionaryAlgorithmException.class, ()->{
                int[][] optimizedPopulation =algorithm.optimize(population);
            });
        }
        @Test
        @DisplayName("Throws EvolutionaryAlgorithmException with a population with empty attributes")
        void optimize_emptyAttributesPopulation_throwsEvolutionaryAlgorithmException(){
            int[][] population = {{},{}};
            assertThrows(EvolutionaryAlgorithmException.class, ()->{
                int[][] optimizedPopulation =algorithm.optimize(population);
            });
        }

        @Test
        @DisplayName("Throws EvolutionaryAlgorithmException with a population with single individual")
        void optimize_singleIndividualPopulation_throwsEvolutionaryAlgorithmException() {
            int[][] population = {{4,5,6}};

            assertThrows(EvolutionaryAlgorithmException.class, ()->{
                int[][] optimizedPopulation = algorithm.optimize(population);
            });
        }

        @Test
        @DisplayName("Throws EvolutionaryAlgorithmException with an odd population")
        void optimize_oddPopulation_throwsEvolutionaryAlgorithmException() {
            int[][] population = {{1,2,3}, {4,5,6}, {7,8,9}};

            assertThrows(EvolutionaryAlgorithmException.class, ()->{
                int[][] optimizedPopulation = algorithm.optimize(population);
            });
        }

        @Test
        @DisplayName("Throws EvolutionaryAlgorithmException with a population with different attribute's sizes")
        void optimize_differentAttributeSizesPopulation_throwsEvolutionaryAlgorithmException(){
            int[][] population = {{1,2},{1}};
            assertThrows(EvolutionaryAlgorithmException.class, ()->{
                int[][] optimizedPopulation =algorithm.optimize(population);
            });
        }

        @Test
        @DisplayName("Returns a population of the same size with a single attribute population")
        void optimize_singleAttributePopulation_returnsPopulationTheSameSize() throws EvolutionaryAlgorithmException {
            int[][] population = {{1},{4},{7},{3}};
            int expectedPopulationLength = population.length;
            int expectedAttributesLength = population[0].length;

            int[][] optimizedPopulation = algorithm.optimize(population);

            assertEquals(expectedPopulationLength, optimizedPopulation.length);
            assertEquals(expectedAttributesLength, optimizedPopulation[0].length);
            assertEquals(expectedAttributesLength, optimizedPopulation[1].length);
            assertEquals(expectedAttributesLength, optimizedPopulation[2].length);
            assertEquals(expectedAttributesLength, optimizedPopulation[3].length);
        }

        @Test
        @DisplayName("Returns a population of the same size with a valid population")
        void optimize_validPopulation_returnsPopulationTheSameSize() throws EvolutionaryAlgorithmException {
            int[][] population = {{1,2,3,4,5,6},{7,8,9,10,11,12},{13,14,15,16,17,18},{19,20,21,22,23,24}};
            int expectedPopulationLength = population.length;
            int expectedAttributesLength = population[0].length;

            int[][] optimizedPopulation = algorithm.optimize(population);

            assertEquals(expectedPopulationLength, optimizedPopulation.length);
            assertEquals(expectedAttributesLength, optimizedPopulation[0].length);
            assertEquals(expectedAttributesLength, optimizedPopulation[1].length);
            assertEquals(expectedAttributesLength, optimizedPopulation[2].length);
            assertEquals(expectedAttributesLength, optimizedPopulation[3].length);
        }

    }
}

