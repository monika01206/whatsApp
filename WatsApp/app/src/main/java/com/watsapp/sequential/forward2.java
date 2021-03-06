package com.watsapp.sequential;

import com.watsapp.prelude.BRelation;
import com.watsapp.prelude.BSet;
import com.watsapp.prelude.Pair;

public class forward2{
	/*@ spec_public */ private machine3 machine; // reference to the machine 

	/*@ public normal_behavior
		requires true;
		assignable \everything;
		ensures this.machine == m; */
	public forward2(machine3 m) {
		this.machine = m;
	}

	/*@ public normal_behavior
		requires true;
 		assignable \nothing;
		ensures \result <==> (machine.get_user().has(u) && ul.isProperSubset(machine.get_user()) && (BRelation.cross(new BSet<Integer>(u),ul).intersection(machine.get_muted())).equals(BSet.EMPTY) && machine.get_content().has(c) && machine.get_chatcontent().apply(u).domain().has(c) && BRelation.cross(new BSet<Integer>(u),ul).isSubset(machine.get_chat()) && (BRelation.cross(ul,new BSet<Integer>(u)).intersection(machine.get_muted())).equals(BSet.EMPTY)); */
	public /*@ pure */ boolean guard_forward2( Integer c, Integer u, BSet<Integer> ul) {
		return (machine.get_user().has(u) && ul.isProperSubset(machine.get_user()) && (BRelation.cross(new BSet<>(u),ul).intersection(machine.get_muted())).equals(BSet.EMPTY) && machine.get_content().has(c) && machine.get_chatcontent().apply(u).domain().has(c) && BRelation.cross(new BSet<>(u),ul).isSubset(machine.get_chat()) && (BRelation.cross(ul, new BSet<>(u)).intersection(machine.get_muted())).equals(BSet.EMPTY));
	}

	/*@ public normal_behavior
		requires guard_forward2(c,u,ul);
		assignable machine.chat, machine.chatcontent, machine.toread, machine.inactive;
		ensures guard_forward2(c,u,ul) &&  machine.get_chat().equals(\old((machine.get_chat().union(BRelation.cross(ul,new BSet<Integer>(u)))))) &&  machine.get_chatcontent().equals(\old((machine.get_chatcontent().override(new BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>>(new Pair<Integer,BRelation<Integer,BRelation<Integer,Integer>>>(u,(machine.get_chatcontent().apply(u).domainSubtraction(new BSet<Integer>(c)).union(new BRelation<Integer,BRelation<Integer,Integer>>(new Pair<Integer,BRelation<Integer,Integer>>(c,(machine.get_chatcontent().apply(u).apply(c).union((BRelation.cross(ul,new BSet<Integer>(u)).union(BRelation.cross(new BSet<Integer>(u),ul))))))))))))))) &&  machine.get_toread().equals(\old((machine.get_toread().union(BRelation.cross(ul,new BSet<Integer>(u)).difference(machine.get_active()))))) &&  machine.get_inactive().equals(\old(machine.get_inactive().difference(BRelation.cross(ul,new BSet<Integer>(u))))); 
	 also
		requires !guard_forward2(c,u,ul);
		assignable \nothing;
		ensures true; */
	public void run_forward2( Integer c, Integer u, BSet<Integer> ul){
		if(guard_forward2(c,u,ul)) {
			BRelation<Integer,Integer> chat_tmp = machine.get_chat();
			BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> chatcontent_tmp = machine.get_chatcontent();
			BRelation<Integer,Integer> toread_tmp = machine.get_toread();
			BRelation<Integer,Integer> inactive_tmp = machine.get_inactive();

			machine.set_chat((chat_tmp.union(BRelation.cross(ul, new BSet<>(u)))));
			machine.set_chatcontent((chatcontent_tmp.override(new BRelation<>(new Pair<>(u, (chatcontent_tmp.apply(u).domainSubtraction(new BSet<>(c)).union(new BRelation<>(new Pair<>(c, (chatcontent_tmp.apply(u).apply(c).union((BRelation.cross(ul, new BSet<>(u)).union(BRelation.cross(new BSet<>(u), ul))))))))))))));
			machine.set_toread((toread_tmp.union(BRelation.cross(ul, new BSet<>(u)).difference(machine.get_active()))));
			machine.set_inactive(inactive_tmp.difference(BRelation.cross(ul, new BSet<>(u))));

			System.out.println("forward2 executed c: " + c + " u: " + u + " ul: " + ul + " ");
		}
	}

}
