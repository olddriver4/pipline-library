def triggers() {
    triggers{
        GenericTrigger(
            genericVariables: [
            [key: 'branch', value: '$.ref']
            ],
            causeString: 'Triggered on $branch',
            token: "${env.TOKEN}", //变更
            printContributedVariables: true,
            printPostContent: true,
            silentResponse: false,
            regexpFilterText: '$branch',
            regexpFilterExpression: "^refs/heads/(${env.TRIGGER_BRANCH})$" //变更
        )
    }
}