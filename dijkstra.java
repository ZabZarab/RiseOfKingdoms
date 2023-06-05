 public List<Vertex> dijkstra(Vertex start, Vertex end){
	  List<Vertex> finalPath = new List<>();
	  
	  vertices.toFirst();
	  while(vertices.hasAccess()) {
		  vertices.getContent().setMark(false);
		  vertices.getContent().setScore(Integer.MAX_VALUE);
		  vertices.next();
	  }
	  start.setScore(0);
	  
	  while(true) {
		  Vertex current = nodeWithLowestScore();
		  current.setMark(true);
		  
		  List<Vertex> neighbours = getNeighbours(current);
		  neighbours.toFirst();
		  while(neighbours.hasAccess()) {
			  if(!neighbours.getContent().isMarked()) {
				  int newScore = neighbours.getContent().getScore()+1;
				  if(newScore < neighbours.getContent().getScore()) {
					  neighbours.getContent().setScore(newScore);
					  finalPath.append(neighbours.getContent());
				  }
			  }
			  neighbours.next();
		  }
		  
		  if(current == end) return finalPath;
		  if(nodeWithLowestScore().getScore() == Integer.MAX_VALUE) return null;
	  }
  }
  
  private Vertex nodeWithLowestScore() {
	  vertices.toFirst();
	  Vertex result = vertices.getContent();
	  
	  while(vertices.hasAccess()) {
		  if(!vertices.getContent().isMarked() && 
				  vertices.getContent().getScore() <= result.getScore()) {
			  result = vertices.getContent();
		  }
		  vertices.next();
	  }
	  
	  return result;
  }
