import random
simulationTypeNo = int(input("Choose the number corresponding to the simulation: \n 1:PERCOLATION \n 2:GAME OF LIFE \n 3:SEGREGATION \n 4:PREDATOR/PREY \n 5:SPREADING FIRE \n    YOUR INPUT: "))-1
nameOfSimulationsList = ["PERCOLATION","GAME OF LIFE","SEGREGATION","PREDATOR/PREY","SPREADING FIRE"]
simulation_type=nameOfSimulationsList[simulationTypeNo]
print("Chosen Simulation Type:" + simulation_type + "\n")
print("Enter Grid Dimensions:") 
rowNo = int(input("  Number of rows: "))
colNo = int(input("  Number of cols: "))
stateTypesListForAll = [["EMPTY","FULL","PERCOLATED"],["ALIVE","DEAD"],["X","O","EMPTY"],["EMPTY","SHARK","FISH"],["EMPTY","TREE","BURNING"]]
stateTypesForThis = stateTypesListForAll[simulationTypeNo]
extraParamListForAll = [[],[],["satisfaction_percentage"],["min_fish_turn_to_breed","max_shark_turns","min_shark_turns_to_breed"],["prob_catch","prob_grow"]]
extraParamForThis = extraParamListForAll[simulationTypeNo]
print("\nState Types for the simulation are: " + " ".join(stateTypesForThis))
print("Enter Cell Type Distribution: \n")
parameter_weights=[]
parameter_vals=[]
for i in range(len(stateTypesForThis)):
	parameter_weights.append(int(input("  Enter Occurance Rate for "+stateTypesForThis[i]+":  ")))
for i in range(len(extraParamForThis)):
        parameter_vals.append(float(input("  Enter Parameter Value of "+extraParamForThis[i]+":  ")))
f= open("output.xml","w+")
f.write('<?xml version="1.0"?>\n')
f.write('<simulation>\n')
f.write('   <simulation_type id="'+str(simulation_type)+'">\n')
f.write('      <author>Simulation Group 6</author>\n')
f.write('      <title>Percolation Simulation</title>\n')
f.write('      <width>'+str(colNo)+'</width>\n')
f.write('      <height>'+str(rowNo)+'</height>\n')
for i in range(len(parameter_vals)):
	f.write('      <'+extraParamForThis[i]+'>'+str(parameter_vals[i])+'</'+extraParamForThis[i]+'>\n')
for i in range(colNo*rowNo):
    if(simulationTypeNo==4 and (i//colNo==0 or i//colNo==rowNo-1 or i%colNo==0 or i%colNo==colNo-1)):
      thisState = ["EMPTY"]
    else:
    	thisState = random.choices(stateTypesForThis,parameter_weights)
    f.write('      <cell id = "'+str(i)+'">\n')
    f.write('         <state>'+thisState[0]+'</state>\n')
    f.write('      </cell>\n')
f.write('   </simulation_type >\n')
f.write('</simulation>\n')
f.close()
