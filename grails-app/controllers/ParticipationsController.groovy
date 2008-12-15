class ParticipationsController {
    
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    def allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        if(!params.max) params.max = 10
        [ participationInstanceList: Participation.list( params ) ]
    }

    def show = {
        def participationInstance = Participation.get( params.id )

        if(!participationInstance) {
            flash.message = "Participation not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ participationInstance : participationInstance ] }
    }

    def delete = {
        def participationInstance = Participation.get( params.id )
        if(participationInstance) {
            participationInstance.delete()
            flash.message = "Participation ${params.id} deleted"
            redirect(action:list)
        }
        else {
            flash.message = "Participation not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def participationInstance = Participation.get( params.id )

        if(!participationInstance) {
            flash.message = "Participation not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ participationInstance : participationInstance ]
        }
    }

    def update = {
        def participationInstance = Participation.get( params.id )
        if(participationInstance) {
            participationInstance.properties = params
            if(!participationInstance.hasErrors() && participationInstance.save()) {
                flash.message = "Participation ${params.id} updated"
                redirect(action:show,id:participationInstance.id)
            }
            else {
                render(view:'edit',model:[participationInstance:participationInstance])
            }
        }
        else {
            flash.message = "Participation not found with id ${params.id}"
            redirect(action:edit,id:params.id)
        }
    }

    def create = {
        def participationInstance = new Participation()
        participationInstance.properties = params
        return ['participationInstance':participationInstance]
    }

    def save = {
        def participationInstance = new Participation(params)
        if(!participationInstance.hasErrors() && participationInstance.save()) {
            flash.message = "Participation ${participationInstance.id} created"
            redirect(action:show,id:participationInstance.id)
        }
        else {
            render(view:'create',model:[participationInstance:participationInstance])
        }
    }
}
