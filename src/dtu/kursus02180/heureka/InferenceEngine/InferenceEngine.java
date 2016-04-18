package dtu.kursus02180.heureka.InferenceEngine;

import dtu.kursus02180.heureka.Graph.Edge;
import dtu.kursus02180.heureka.Graph.Graph;
import dtu.kursus02180.heureka.Graph.Node;

import java.util.PriorityQueue;

public class InferenceEngine {

    public static Clause solve(KnowledgeBase knowledgeBase, Clause solveOriginal, Graph graph){


        // Negate solving clause
        Clause solve = new Clause();
        solve.positiveLiterals.addAll(solveOriginal.negativeLiterals);
        solve.negativeLiterals.addAll(solveOriginal.positiveLiterals);

        graph.addNode(solve);

        PriorityQueue<Node> priorityQueue = new PriorityQueue<Node>();
        solve.setDistance(0);
        priorityQueue.add(solve);

        while (!priorityQueue.isEmpty()){
            Clause clause = (Clause) priorityQueue.poll();

            for (Clause rule : knowledgeBase.list){

                Clause newResolvent = clause.applyRule(rule);

                graph.addNode(newResolvent);
                Edge newEdge = graph.addEdge(clause, newResolvent, 1);

                newResolvent.relax(clause, null, newEdge, priorityQueue);

                if (newResolvent.isEmptyClause()){
                    return newResolvent;
                }
            }
        }

        return null;
    }
}