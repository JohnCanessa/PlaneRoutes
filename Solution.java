import java.util.*;


/*
 * Build a list of optimal routes for a plane given its range.
 */
public class Solution {

	// **** ****
	static final boolean	REMOVE_NON_OPTIMAL	= false;
	
	
	/*
	 * Remove previous range if smaller than the new one.
	 */
	static ArrayList<ArrayList<Integer>> removePrevRange(	ArrayList<ArrayList<Integer>> routes,
															int newRange) {
		
		// **** check for empty routes ****
		if (routes.size() == 0)
			return routes;
		
		// **** ****
		ArrayList<Integer> optimalRange = routes.get(0);
		
//		// ???? ????
//		System.out.println("removePrevRange <<< optimalRange: " + optimalRange.toString() + " newRange: " + newRange);

		// **** remove route (if needed) ****
		if (newRange > optimalRange.get(2))
			routes.remove(0);
		
		// **** ****
		return routes;
	}
	
	
	/*
	 * Add new best route.
	 */
	static ArrayList<ArrayList<Integer>> addBetterRoute(ArrayList<ArrayList<Integer>> routes,
														ArrayList<Integer> outgoing,
														ArrayList<Integer> incoming) {
				
		// **** extract the outgoing route and range ****
		int outRoute = outgoing.get(0);
		int outRange = outgoing.get(1);

		// **** extract the incoming route and range ****
		int inRoute = incoming.get(0);
		int inRange = incoming.get(1);
		
		// **** build new route ****
		ArrayList<Integer> route = new ArrayList<Integer>();
		
		// **** populate new route ****
		route.add(outRoute);
		route.add(inRoute);
		route.add(outRange + inRange);
		
//		// ???? ????
//		System.out.println("addBetterRoute <<< route: " + route.toString());
		
		// **** add new route **** 
		routes.add(route);

		// **** ****
		return routes;
	}

	
	/*
	 * Remove non-optimal routes.
	 */
	static ArrayList<ArrayList<Integer>> removeNonOptimal(	ArrayList<ArrayList<Integer>> routes,
															int planeRange) {
	
		// **** check if routes is empty ****
		if (routes.size() == 0)
			return routes;	
		
		// **** traverse list of routes ****
		int i = 0;
		do	{
			// **** get the current route ****
			ArrayList<Integer> route = routes.get(i);
			
//			// ???? ????
//			System.out.println("removeNonOptimal <<< route: " + route.toString());

			// **** if non-optimal then remmove route ****
			int range = route.get(2);
			if (range < planeRange)
				routes.remove(i);
			else
				i++;
		} while (i < routes.size());

		// **** ****
		return routes;
	}
	
	
	/*
	 * Select the best route(s) for this plane.
	 * The outgoing and incoming routes are in monotonically ascending order.
	 */
	static ArrayList<ArrayList<Integer>> bestRoutes(ArrayList<ArrayList<Integer>> outgoingRoutes,
													ArrayList<ArrayList<Integer>> incomingRoutes,
													int	planeRange) {
		
		// **** ****
		ArrayList<ArrayList<Integer>> routes = new ArrayList<ArrayList<Integer>>();
		
		// **** traverse the outgoing routes ****
		for (int i = 0; i < outgoingRoutes.size(); i++) {
			
			// **** get the outgoing route ****
			ArrayList<Integer> outgoing = outgoingRoutes.get(i);
			
//			// ???? ????
//			System.out.println("bestRoutes <<< outgoing: " + outgoing.toString());
			
			// **** extract the outgoing route and range ****
			int outRoute = outgoing.get(0);
			int outRange = outgoing.get(1);
			
//			// ???? ????
//			System.out.println("bestRoutes <<< outRoute: " + outRoute + " outRange: " + outRange);
			
			// **** check if the plane is out of range ****
			if (outRange > planeRange)
				break;
			
			// **** traverse the incomming routes ****
			for (int j = 0; j < incomingRoutes.size(); j++) {
				
				// **** get the incoming route ****
				ArrayList<Integer> incoming = incomingRoutes.get(j);
				
//				// ???? ????
//				System.out.println("bestRoutes <<< incoming: " + incoming.toString());
			
				// **** extract the incoming route and range ****
				int inRoute = incoming.get(0);
				int inRange = incoming.get(1);
				
//				// ???? ????
//				System.out.println("bestRoutes <<< inRoute: " + inRoute + " inRange: " + inRange);
				
				// **** check if the route is out of range ****
				if (outRange + inRange > planeRange) {
					
//					// ???? ????
//					System.out.println("bestRoutes <<< outRange + inRange: " + (outRange + inRange) + " > planeRange: " + planeRange);
					
					break;
				}
								
				// **** remove previous route (if shorter than this route) ****
				routes = removePrevRange(	routes,
											outRange + inRange);
	
//				// ???? ????
//				System.out.println("bestRoutes <<< routes: " + routes.toString());
							
				// **** check if this route is shorter than the best one ****
				if ((routes.size() != 0) && 
					(outRange + inRange < routes.get(0).get(2)))
					continue;

				// **** add better route ****
				routes = addBetterRoute(routes,
										outgoing,
										incoming);

//				// ???? ????
//				System.out.println("bestRoutes <<< routes: " + routes.toString());
			}
			
//			// ???? ????
//			System.out.println();
		}
			
//		// ???? ????
//		System.out.println("bestRoutes <<< routes: " + routes.toString());
		
		// **** remove non-optimal routes (if needed) ****
		if (REMOVE_NON_OPTIMAL)
			routes = removeNonOptimal(	routes,
										planeRange);
		
//		// ???? ????
//		System.out.println("bestRoutes <<< routes: " + routes.toString());

		// **** ****
		return routes;
	}

	
	/*
	 * Test scaffolding.
	 */
	public static void main(String[] args) {

		// **** ****
		ArrayList<ArrayList<Integer>> outgoingRoutes = new ArrayList<ArrayList<Integer>>();
		
		ArrayList<ArrayList<Integer>> incomingRoutes = new ArrayList<ArrayList<Integer>>();
		
		int planeRange	= -1;
		
		// **** open scannner ****
		final Scanner sc = new Scanner(System.in);
		
		// **** number of outgoing routes ****
		int n = sc.nextInt();
				
		// **** populate outgoing routes ****
		for (int i = 0; i < n; i++) {
			
			// **** route number ****
			int r = sc.nextInt();
			
			// **** distance ****
			int distance = sc.nextInt();
			
			// **** ****
			ArrayList<Integer> route = new ArrayList<>();
			route.add(r);
			route.add(distance);
			
			outgoingRoutes.add(route);
		}
		
		// ???? ????
		System.out.println("main <<< outgoingRoutes: " + outgoingRoutes.toString());
		
		// **** number of incoming routes ****
		n = sc.nextInt();
		
		// **** populate incoming routes ****
		for (int i = 0; i < n; i++) {
			
			// **** route number ****
			int r = sc.nextInt();
			
			// **** distance ****
			int distance = sc.nextInt();
		
			// **** ****
			ArrayList<Integer> route = new ArrayList<>();
			route.add(r);
			route.add(distance);
			
			incomingRoutes.add(route);
		}
		
		// ???? ????
		System.out.println("main <<< incomingRoutes: " + incomingRoutes.toString());
			
		// **** populate plane range ****
		planeRange = sc.nextInt();

		// **** close scanner ****
		sc.close();
		
		// ???? ????
		System.out.println("main <<<     planeRange: " + planeRange);
		
		// **** ****
		ArrayList<ArrayList<Integer>> routeList = bestRoutes(	outgoingRoutes,
																incomingRoutes,
																planeRange);
		
		// **** display the best routes ****
		System.out.println("main <<<      routeList: " + routeList.toString());
	}

}
