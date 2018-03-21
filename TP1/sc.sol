pragma solidity ^0.4.16;

contract SystemeVote {
    
    // VARIABLES


    address public admin;
    mapping(address=>uint) public listeElecteur;
    mapping(address=>uint) public listeCandidat;
    address [] public Candidataddress;
    //Candidat[] public listeCandidat;
    
    // MODIFIER
    modifier isadmin(){
        if(msg.sender!=admin){
            throw;
            _;
        }
    }
        

    //function      
    function SystemeVote(address[] electeurs, address[] candidats) public {
            admin=msg.sender;
            listeElecteur[admin]=1;
            
            for (uint i = 0; i < candidats.length; i++) {
                
                RecordCandidat(candidats[i]);
                RecordAddress(candidats[i],i);
            }
            for (uint j = 0; j < electeurs.length; j++) {
                
                RecordElecteur(electeurs[j]);
            }
            
        }
        
    
        function RecordAddress(address candidat,uint index) public{
            Candidataddress[index]=candidat;
        }
        
        function RecordElecteur(address electeur) public{
            
           
                listeElecteur[electeur]=1;
            } 

        
        function RecordCandidat(address candidat) public {
            
            listeCandidat[candidat]=0;
            
             }
        
        
        function vote(address voterpour) public{
            if(listeElecteur[msg.sender]>0){
            listeElecteur[msg.sender]=listeElecteur[msg.sender]-1;
            listeCandidat[voterpour]=listeCandidat[voterpour]+1;
            }
        }
        
        function delegation(address deleguerto) public{
            require(listeElecteur[msg.sender]!=0);
            require(deleguerto != msg.sender);
            listeElecteur[deleguerto]=listeElecteur[msg.sender]+listeElecteur[deleguerto];
            listeElecteur[msg.sender]=0;

        }
         function winner() public view
            returns (address)
    {
        address _winner;
        uint winningVoteCount=0;
        uint winningProposal;
       for( uint p = 0; p < Candidataddress.length; p++) {
            if (listeCandidat[Candidataddress[p]] > winningVoteCount) {
                winningVoteCount = listeCandidat[Candidataddress[p]];
                winningProposal = p;
                _winner=Candidataddress[p];
            }
        }
        return _winner;
    }
        
    // KILL
    function kill() isadmin(){
        
        delete admin;
        selfdestruct(msg.sender);
    }
    
    }
