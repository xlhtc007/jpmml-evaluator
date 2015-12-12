/*
 * Copyright (c) 2015 Villu Ruusmann
 *
 * This file is part of JPMML-Evaluator
 *
 * JPMML-Evaluator is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JPMML-Evaluator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with JPMML-Evaluator.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.jpmml.rattle;

import java.util.Deque;
import java.util.List;

import org.dmg.pmml.MiningFunctionType;
import org.dmg.pmml.Node;
import org.dmg.pmml.PMMLObject;
import org.dmg.pmml.ScoreDistribution;
import org.dmg.pmml.TreeModel;
import org.dmg.pmml.VisitorAction;
import org.jpmml.model.visitors.AbstractVisitor;

public class NodeTransformer extends AbstractVisitor {

	@Override
	public VisitorAction visit(Node node){
		TreeModel treeModel = getParent(TreeModel.class);

		if(treeModel != null){
			MiningFunctionType miningFunction = treeModel.getFunctionName();

			switch(miningFunction){
				case REGRESSION:
					if(node.hasScoreDistributions()){
						List<ScoreDistribution> scoreDistributions = node.getScoreDistributions();

						scoreDistributions.clear();
					}
					break;
				default:
					break;
			}
		}

		return super.visit(node);
	}

	private <E> E getParent(Class<? extends E> clazz){
		Deque<PMMLObject> parents = getParents();

		for(PMMLObject parent : parents){

			if(clazz.isInstance(parent)){
				return clazz.cast(parent);
			}
		}

		return null;
	}
}