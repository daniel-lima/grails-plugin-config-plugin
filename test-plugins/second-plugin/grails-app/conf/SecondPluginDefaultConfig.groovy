grails {
    plugins {        
        second {
            value1 = 'abc'
            value2 = "${grails.plugins.third.value1}-2"
        }
    }
}