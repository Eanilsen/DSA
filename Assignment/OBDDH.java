// Class file for the the OBDDH diagram.
//
// The idea is that a diagram is created (which loads in the network and then sets up the structures). Then compute
// is called, which gets the class to actually generate all of the nodes and also computes the reliability. Finally,
// the reliability is displayed to the screen.
 
import java.util.*;

// Add code where appropriate but do not change existing code without written permission
//
// This code isn't entirely complete but is usable

class OBDDH
{
  private FloatingPoint reliability;
  private Qc current;
  private Qn next;
  private Network net;
  private static int DEFAULT_Qn = 101;	// Default size of Qn. Small but non-trivial
  Vertex v;
  Vertex nextV;

  // The default constructor creates the root node of the OBDDH diagram
  public OBDDH( String filename ) 
  {
    // First load in the network
    net = new Network( filename );
    nextV = net.nextVertex();

    // Then set up the rest of the structures
    reliability = new FloatingPoint( 1.0f );   // Will be adding to this number
    next = new Qn( );                          // Initialize the next queue to be empty
    HNode root = new HNode( nextV ); 	       // Set up the root node of the diagram
    current = new Qc( root );                  // Initialize the current queue to contain the root node

    // Start the computation
    compute();
  }

  // Computes the reliability of the network, which must have been initialized before this is called
  private void compute() 
  {
    HNode node, child;
    Vertex from;
    Vertex prev = null;
    Edge adjacentEdges[];

    // These variables are for the adjacency fix. *sigh*
    Edge processedEdges[] = new Edge[net.getEdgeCount()];
    Edge tempEdges[] = new Edge[net.getEdgeCount()];
    int numProcessedEdges = 0;

    // This is a simplification of the full redundancy system
    HState.setDelFrom();
    HState.setAddTo();

    while ( !current.isEmpty() ) // Keep going through levels until all nodes are processed
    {
      int ii, jj, count;
      boolean found;

// HHH - todo - add a try/catch statement here
      v = nextV;
      nextV = net.nextVertex();

      if ( nextV == null ) // we now cannot reach further vertices, assume all remaining nodes are failures
      {
        while ( !current.isEmpty() )
        {
          node = current.front();
        }
      }
      adjacentEdges = net.adjacentE( v ); // Get all adjacent edges
      // Due to me not specifying the adjacentEdges method correctly, it may contain edges that have already been
      // processed. This means I'll have to remove those myself. I'll also have to do that in a way local to this method
      // since there are no provided methods in network. This is going to hurt performance, but is better than making
      // students re-write their code at this stage
      count = 0;
      for ( ii = 0; ii < adjacentEdges.length; ii++ )
      {
        // Check to see if this edge has already been processed
        found = false;
        for ( jj = 0; !found && (jj < numProcessedEdges); jj++ )
        {
          if ( adjacentEdges[ii].getName().equals( processedEdges[jj].getName() ) )
          {
            found = true; // It's already been processed - ignore
          }
        }
        // If not, add it to the new array and to processed edges
        if ( !found )
        {
          tempEdges[ count ] = adjacentEdges[ii];
          count++;
          processedEdges[ numProcessedEdges ] = adjacentEdges[ii];
          numProcessedEdges++;
        }
      }

      // All edges we actually want should now be in tempEdges. Re-define adjacentEdges accordingly
      if ( adjacentEdges.length != count )
      {
        adjacentEdges = new Edge[count];
        for ( ii = 0; ii < count; ii++ )
        {
          adjacentEdges[ii] = tempEdges[ii];
        }
      }
      // End of annoying fix :(
      

      while ( !current.isEmpty() ) // Work on the current level until all nodes are processed
      {
        node = current.front(); // Remove the first node from the current level
        child = node.makePos( v, adjacentEdges );
        addToQn( child );
        node.makeNeg( v, adjacentEdges );      // Transform node into its own negative child
        addToQn( node );
      } // End of loop over current queue

      // We now move on to the next level of the diagram. This means putting the nodes from Qn onto Qc.
      // Qn becomes empty.
      next.swap( current );
      // After the swap, the only way for Qc to be empty is if both Qc and Qn are empty. When this happens we're done.
    }
  }

  // Checks to see if the node is terminal. If not, attempts to add the node to Qn. If terminal and successful, record the
  // reliability.
  private void addToQn( HNode n ) 
  {
//stub
  }

  // Displays the reliability of the network to the screen
  // Note that the marking scripts expect exactly this format. Students should not write anything to output -
  // the only output should be through this method unless an exception occurs.
  public void displayRel() 
  {
    System.out.println("The reliability of the network is "+reliability.toString());
  }

  // Initializes the network by calling the constructor to load information from a file
  private void loadNetwork( String filename ) 
  {
// Stub
  }


} // end of class OBDDH
