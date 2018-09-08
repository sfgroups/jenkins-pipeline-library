
def playbook_file= "playbook.yaml"
def hosts_file='hosts'
def valut_file='/var/lib/jenkins/.vault_pass'

def playbook='''
---
- hosts: example
  gather_facts: no
  tasks:
    - debug:
        msg: "version {{ version }}"

    - debug:
        msg: "Path {{ path }}"


    - name: print user
      file:
        path: /tmp/abc
        state: touch
'''

def hosts='''
[example]
192.168.16.15
'''

def all='''
$ANSIBLE_VAULT;1.1;AES256
38636661323963393738333664386134613466653032376135666439343137623434306231323966
6330636463303434626339656534343366633638386237360a643733373462323030666335363065
62323832343033663738333861386633616661316435623631333637343665323130353061373231
3730373031366266630a383937666435363935393739376238633939636134303732653138336631
38356337646664346431303265663162356235383864653236616332623638366561616564623931
31373837626135623838396132303934633262633833646665633639353736316166613433623232
63613964393364373830316265666533353263393130326264376134613366386236616339626637
35343132633231323336
'''

def VERSION='34.56.56'
def INSTALLPATH='c:\\temp\\web'

node {
    stage('Create Ansbile file'){
        writeFile file: playbook_file, text: playbook
        writeFile file: hosts_file, text: hosts.trim()
        writeFile file: valut_file, text: 'test'
        dir ('group_vars') {
            writeFile file:'all', text: all.trim()
        }
    }
    stage('Run Ansible Playbook') {
        ansiColor('xterm') {
            ansiblePlaybook(
                    playbook: playbook_file,
                    inventory: hosts_file,
                    colorized: true,
                    disableHostKeyChecking: true,
                    vaultCredentialsId: 'ANSIBLE_VAULT_PASSWORD',
                    extras: "--extra-vars \'version=$VERSION path=$INSTALLPATH\'" )
        }
    }
}